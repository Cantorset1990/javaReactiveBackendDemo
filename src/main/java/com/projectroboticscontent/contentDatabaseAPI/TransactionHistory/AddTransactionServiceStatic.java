package com.projectroboticscontent.contentDatabaseAPI.TransactionHistory;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class AddTransactionServiceStatic {

    private static final  MongoClient historyClient = MongoClients.create();
    private static final  ReactiveMongoTemplate historyMongoOps = new ReactiveMongoTemplate(historyClient,
            "testTransactionHistoryDatabase");


    private static final  MongoClient  registrationClient = MongoClients.create();
    private static final   ReactiveMongoTemplate  registrationMongoOps = new ReactiveMongoTemplate(registrationClient,
            "testLoginDatabase");




    private static Mono<String> getbalance(String WALLET_ID)
    {


        final String url = Configuration.blockchainIPAddress +"/sol/get-balance";

        WebClient balanceClient = WebClient.create();


                            return balanceClient
                                    .get()
                                    .uri(url+"?address="+WALLET_ID)
                                    .retrieve()
                                    .bodyToMono(String.class);


    }


    private static Mono<String> AddTransaction(String RECEIVER_WALLET_ID, String SENDER_WALLET_ID, long RECEIVER_TRANSACTION_AMOUNT,
                                       long SENDER_TRANSACTION_AMOUNT, String NEW_RECEIVER_BALANCE,
                                       String NEW_SENDER_BALANCE, long PROPERTY)
    {

        final String DATETIME = LocalDateTime.now().toString();

        long TRANSACTION_ID = System.currentTimeMillis();

        final HistoryInfo info = new HistoryInfo(RECEIVER_WALLET_ID, SENDER_WALLET_ID, RECEIVER_TRANSACTION_AMOUNT,
         SENDER_TRANSACTION_AMOUNT, NEW_RECEIVER_BALANCE,
         NEW_SENDER_BALANCE,  TRANSACTION_ID,  DATETIME, PROPERTY);

        WebClient addBlockClient = WebClient.create();

        final String url = Configuration.blockchainIPAddress + "/sol/set";

        return historyMongoOps.insert(info ,"HistoryCollection")
                .flatMap(output->{



                                return addBlockClient
                                        .get()
                                        .uri(url+"?key=" + Long.toString(TRANSACTION_ID) + "&value="
                                        +output.getHASH())
                                        .retrieve()
                                        .bodyToMono(String.class);


                })
                .switchIfEmpty(Mono.just("error"));


    }

    private static Mono<String> AddTransaction2(String RECEIVER_WALLET_ID, String SENDER_WALLET_ID, long RECEIVER_TRANSACTION_AMOUNT,
                                       long SENDER_TRANSACTION_AMOUNT,
                                       String NEW_SENDER_BALANCE, long PROPERTY)
    {

        return getbalance(RECEIVER_WALLET_ID)
                .flatMap(output->{

                return AddTransaction(RECEIVER_WALLET_ID, SENDER_WALLET_ID,  RECEIVER_TRANSACTION_AMOUNT,
                     SENDER_TRANSACTION_AMOUNT,  (String) output,
                             NEW_SENDER_BALANCE,  PROPERTY);


                })
                .switchIfEmpty(Mono.just("error"));


    }


    public static Mono<String> AddTransaction3(String RECEIVER_WALLET_ID, String SENDER_WALLET_ID,
                                       long TRANSACTION_AMOUNT, long PROPERTY)

    {

        return getbalance(SENDER_WALLET_ID)
                .flatMap(output->{

                    return AddTransaction2(RECEIVER_WALLET_ID, SENDER_WALLET_ID,  TRANSACTION_AMOUNT,
                            -TRANSACTION_AMOUNT,
                            (String) output, PROPERTY);


                })
                .switchIfEmpty(Mono.just("error"));


    }

    public static Mono<String> AddTransaction4(String token, String USERTYPE, String RECEIVER_WALLET_ID,
                                       long TRANSACTION_AMOUNT, long PROPERTY)
    {

        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);



        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));



        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(

                        output-> {

                            return AddTransaction3( RECEIVER_WALLET_ID, output.getWALLET_ID(), TRANSACTION_AMOUNT, PROPERTY);


                        }

                )
                .switchIfEmpty(Mono.just("error"));
    }



}
