package com.projectroboticscontent.contentDatabaseAPI.Market;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.TransactionHistory.AddTransactionServiceStatic;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class PurchaseService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    public PurchaseService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testSolutionDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    public Mono<String> createStatusException2(long id){
        return
                Mono.just(String.valueOf(id));
    }

    public Mono<ResponseStatusException> Error(){

        return Mono.just(createStatusException());

    }

    public Mono<String> updateMessage(){

        return Mono.just("Permission Update was successful");
    }

    public Mono<String> SendCoin(String WALLET_ID, String RECEIVER, String AMOUNT)
    {
        WebClient webClientCoin = WebClient.create();

        java.lang.String url = "http://localhost:8080/api/v1/sol/transfer?" +
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
                .bodyToMono(java.lang.String.class);

    }

    public Mono<String> SendCoin2(String token, String USERTYPE, String RECEIVER,
                                                   String AMOUNT)
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

    public Mono<String> sendCoinToSolutionProvider(String token, String USERTYPE,
                                                   long SOLUTION_ID)
    {

        final Query query =

                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)

                        ));

        return MongoOps.findOne(query,
                SolutionMarketSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{
                    return SendCoin2(token,USERTYPE,output.getSOLUTION_PROVIDER_WALLET_ID(),
                            Long.toString(output.getPRICE()));
                })
                .switchIfEmpty(Mono.just("error"));


    }

    public long[] addPurchase(long[] PURCHASE_LIST,  long PRIMARY_KEY)
    {
        long newarr[] = new long[PURCHASE_LIST.length + 1];

        for (int i = 0; i < PURCHASE_LIST.length; i++)
            newarr[i] = PURCHASE_LIST[i];

        newarr[PURCHASE_LIST.length] = PRIMARY_KEY;

        return newarr;
    }

    public Mono<SolutionMarketSubmission> updatePurchase(long[] PURCHASE_LIST, long SOLUTION_ID)
    {
        final Query query =

                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)

                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");

        Update update = new Update();
        update.set("PURCHASE_LIST", PURCHASE_LIST);
        //update.set("FORM_TITLE", FORM_TITLE);

        return MongoOps.findAndModify(query,update, SolutionMarketSubmission.class,"SolutionProviderCollection");

        // .switchIfEmpty(Error());


    }


    public Mono<String> getAndAddPurchase(String token, String USERTYPE, long SOLUTION_ID)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);

        // final long FORM_ID = System.currentTimeMillis();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)

                        ));

        final Query queryProfile =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(PRIMARY_KEY)

                        ));




        return registrationMongoOps.findOne(queryProfile, UserProfile.class,"RegistrationCollection")
                        .flatMap(value->{

                            return MongoOps.findOne(query, SolutionMarketSubmission.class,"SolutionProviderCollection")


                                    .flatMap(output ->{
                                        long [] purchase = addPurchase(output.getPURCHASE_LIST(), PRIMARY_KEY);
                                        return updatePurchase(purchase,SOLUTION_ID);

                                    })

                                    .flatMap(output->{
                                        return SendCoin2(token,USERTYPE,output.getSOLUTION_PROVIDER_WALLET_ID(),
                                                Long.toString(output.getPRICE()));
                                        //return sendCoinToSolutionProvider(token,USERTYPE,SOLUTION_ID);
                                    });


                        })
                        .switchIfEmpty(Mono.just("error"));




    }







}
