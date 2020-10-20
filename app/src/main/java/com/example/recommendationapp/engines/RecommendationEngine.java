package com.example.recommendationapp.engines;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.example.recommendationapp.R;
import com.example.recommendationapp.cls.Movie;
import com.example.recommendationapp.cls.User;


import com.jasongoodwin.monads.Try;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;


import java.util.ArrayList;
import java.util.List;

import scala.Tuple2;


public class RecommendationEngine {

    public static final String moviesPath = "";
    public static final String usersPath = "";
    public static final String ratingsPath = "";

    public void Recommendation(Integer givenMovieId){

        JavaSparkContext jsc = new JavaSparkContext("local", "Recommendation App");
        //SQLContext sqlContext = new SQLContext(jsc);

        //cache는 RDD연산이 끝난 뒤 결과를 cluster의 memory에 임시로 저장하여 유지함
        JavaRDD<Movie> movieRDD = jsc.textFile(moviesPath).map(line -> {
            final String[] movieArray = line.split("::");
            Integer movieId = Integer.parseInt(Try.ofFailable(() -> movieArray[0]).orElse("-1"));
            return new Movie(movieId, movieArray[1], movieArray[2]);
        }).cache();

        JavaRDD<User> userRDD = jsc.textFile(usersPath).map(line->{
            String[] userArray = line.split("::");
            Integer userId = Integer.parseInt(Try.ofFailable(()->userArray[0]).orElse("-1"));
            Integer age = Integer.parseInt(Try.ofFailable(()->userArray[2]).orElse("-1"));
            Integer occupation = Integer.parseInt(Try.ofFailable(() -> userArray[3]).orElse("-1"));
            return new User(userId, userArray[1], age, occupation, userArray[4]);
        }).cache();


        JavaRDD<Rating> ratingRDD = jsc.textFile(ratingsPath).map(line -> {
            String[] ratingArray = line.split("::");
            Integer userId = Integer.parseInt(Try.ofFailable(() -> ratingArray[0]).orElse("-1"));
            Integer movieId = Integer.parseInt(Try.ofFailable(() -> ratingArray[1]).orElse("-1"));
            Double rating = Double.parseDouble(Try.ofFailable(() -> ratingArray[2]).orElse("-1"));
            return new Rating(userId, movieId, rating);
        }).cache();



        JavaRDD<Rating>[] ratingSplits = ratingRDD.randomSplit(new double[] {0.8, 0.2});
        JavaRDD<Rating> ratingTrainingRDD = ratingSplits[0].cache();
        JavaRDD<Rating> ratingTestRDD = ratingSplits[1].cache();

        /*
        Latent factor Models (잠재 요인 모델) - Matrix Factorization(행렬 인수분해)
        항목 등급 패턴으로부터 추론된 인자의 벡터에 의해 아이템과 사용자를 특징짓는다.
        이떄 사용되는 데이터는 명시적 피드백(평점)이며,
        이는 sparse matrix(희박한 행렬)을 구성하게 됨
        (단일 사용자가 가능한 항목의 비율을 낮게 평가할 가능성이 있기 때문)
        */

        /*
        ALS는 user벡터와 item벡터의 곱이 rating에 근접하도록 각 user와 item에 대한 feature vector를 정의
        user와 item data를 matrix X로 표현
        X는 user i가 artist j의 음악을 들었다면 i행, j열에 값이 존재하는 matrix임
        user-artist의 모든 조합에서 각 user가 모든 artist의 음악을 듣지 않고 소수의 음악만 청취하게 됨
        ALS를 동작시키려면 row = user, column = artist, value = rating 인 matrix가 필요
        */


        /*
        Matrix factorization
        아이템 x 유저의 평점 행렬을 분리한 후
        분리된 두 개의 행렬을 다시 합치면
        각 유저별로 평점이 없는 아이템들의 평점 근사값을 계산할 수 있음
        사용자에게는 각 아이템들에 대해서 예측된 평점 순으로 아이템을 추천
        이때 실제 기록된 평점과 분리된 2개의 행렬을 다시 합쳤을 때의 평점 차이가 최소화되도록 학습해야함
        ALS는 사용자 행렬을 상수로 고정시키고 아이템 행렬을 최적화해보고,
        아이템 행렬을 상수로 고정시키고 사용자 행렬을 최적화하는 과정을 반복한다.
        평점이 없는 것 (0) 들은 실제 평점보다 적은 가중치로 비교한다.
        이는 가중치는 적지만 평점이 존재하는 아이템들에 비해 수가 월등히 많기 떄문에 학습에 유의미한 영향을 미친다.
        rank는 높을수록 정교해지나 memory가 증가하고 학습 속도가 느려진다.
         */

        List<String> movieRecommendList = new ArrayList<>();
        MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratingTrainingRDD), 5, 10, 0.01);
        JavaPairRDD<Integer, Integer> testUserProductRDD = ratingTestRDD.mapToPair(rating->
                new Tuple2<>(rating.user(), rating.product()));
        JavaRDD<Rating> predictionsForTestRDD = model.predict(testUserProductRDD);
        predictionsForTestRDD.take(10).stream().forEach(rating -> {
            movieRecommendList.add(Integer.toString(rating.product()));
        });



        //return movieRecommendList;

    }
}
