package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.Session;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.TransactionHistory.AddTransactionServiceStatic;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class TransferService {


    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;


    public TransferService() {
        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }


    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Profile Error\n");
    }

    public Mono<Exception> submissionError(){

        return Mono.just(createStatusException());

    }

    public Mono<String> Error(){

        return Mono.just("error");

    }

    public Mono<UserProfile> checkWalletID(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));




        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection");



    }

    private Mono<UserProfile> checkOtherWalletID(String token, String USERTYPE, String EMAIL)
    {

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("EMAIL").is(EMAIL)

                        ));



        return checkWalletID(token,USERTYPE)
                .flatMap(

                        output->{

                            return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection");

                        }
                );
    }


    public Mono<String> SendCoin(String WALLET_ID, String RECEIVER, String AMOUNT)
    {
        WebClient webClientCoin = WebClient.create();

        String url = "http://localhost:8080/api/v1/sol/transfer?" +
                "address=" +
                WALLET_ID +
                "&receiver=" +
                RECEIVER +
                "&amount=" +
                AMOUNT;


            return webClientCoin
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class);

    }

    public Mono<String> SendCoin2(String token, String USERTYPE, String RECEIVER, String AMOUNT)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));




        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")

                .flatMap(output1->{
                    return SendCoin(output1.getWALLET_ID(),  RECEIVER,  AMOUNT);
                })
                .flatMap(
                        output2->{
                            return AddTransactionServiceStatic.AddTransaction4( token,  USERTYPE,  RECEIVER,
                                    Long.parseLong(AMOUNT), 1);
                        }
                )
                .switchIfEmpty(Mono.just("error"));
                //.switchIfEmpty(Error());

    }

    public Mono<String> SendCoinWithEmail(String token, String USERTYPE, String EMAIL, String AMOUNT)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("EMAIL").is(EMAIL)

                        ));

        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{
                    return SendCoin2( token,  USERTYPE, output.getWALLET_ID(),  AMOUNT);
                })
                .switchIfEmpty(Mono.just("error"));
                //.switchIfEmpty(Error());

    }



}
