package com.projectroboticscontent.contentDatabaseAPI.SolutionProvider;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class UpdateSolutionService {

    private final MongoClient solutionClient;
    private final ReactiveMongoTemplate solutionMongoOps;

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    public  UpdateSolutionService() {

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient, "testSolutionDatabase");

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    public Mono<String> createStatusException2(long id){
        return
                Mono.just(String.valueOf(id));
    }

    public Mono<Exception> Error(){

        return Mono.just(createStatusException());

    }

    public Mono<String> updateMessage(){

        return Mono.just("Update was successful");
    }





    public Mono<String> updateSolution(String token, long SOLUTION_ID,
                               String JSON_SOLUTION_DATA, String SOLUTION_TITLE, String USERTYPE)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));



        Update update = new Update();
        update.set("JSON_FORM_DATA", JSON_SOLUTION_DATA);
        update.set("FORM_TITLE", SOLUTION_TITLE);

        return solutionMongoOps.findAndModify(query,update, SolutionSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{
                    return updateMessage();
                })
                .switchIfEmpty(Mono.just("error"));



    }

    public Mono<String> updateSolutionStatus(String token, long SOLUTION_ID,
                                String USERTYPE, long STATUS)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_PROVIDER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));



        Update update = new Update();
        update.set("STATUS", STATUS);


        return solutionMongoOps.findAndModify(query,update, SolutionSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{
                    return updateMessage();
                })
                .switchIfEmpty(Mono.just("error"));



    }

    public Mono<String> updateSolutionStatusByUser(String token, long SOLUTION_ID,
                                     String USERTYPE, long STATUS)
    {
        final long PRIMARY_KEY = JwtTokenUtil.parseJWT(token,USERTYPE);


        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USER_PRIMARY_KEY").is(PRIMARY_KEY),
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)
                        ));



        Update update = new Update();
        update.set("STATUS", STATUS);


        return solutionMongoOps.findAndModify(query,update, SolutionSubmission.class,"SolutionProviderCollection")
                .flatMap(output->{
                    return updateMessage();
                })
                .switchIfEmpty(Mono.just("error"));



    }





}
