package com.projectroboticscontent.contentDatabaseAPI.Profile;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeleteUserProfileService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    public DeleteUserProfileService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");

        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    public Mono<ResponseStatusException> Error(){

        return Mono.just(createStatusException());

    }

    public Mono<String> terminationMessage(){
        return Mono.just("Profile was terminated");
    }

    public Flux<FormSubmission> deleteAllForms(String token)
    {
        final long ID = JwtTokenUtil.parseJWTProfile(token);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");



        return MongoOps.findAllAndRemove(query, FormSubmission.class,"UserRequestCollection");
               // .switchIfEmpty(Error());


    }

    public Flux<String> deleteUserProfile(String token)
    {
        final long ID = JwtTokenUtil.parseJWTProfile(token);
        //final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");



        return registrationMongoOps.findAllAndRemove(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{
                    return deleteAllForms(token);})
                .flatMap(output->{
                    return terminationMessage();
                })
                .switchIfEmpty(Mono.just("error"));
               // .switchIfEmpty(terminationMessage());


    }

}
