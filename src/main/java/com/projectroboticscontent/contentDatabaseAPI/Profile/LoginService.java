package com.projectroboticscontent.contentDatabaseAPI.Profile;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class LoginService {

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;


    public LoginService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testLoginDatabase");

    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login Error, invalid username and password\n");
    }

    public Mono loginError(){

        return Mono.just(createStatusException());

    }

    public Mono loginErrorMessage(){

        return Mono.just("Login error. Check username and password.");

    }

    public Mono newSession(String[] USERTYPE, long ID, String subject, String WALLET_ID)
    {
        return Mono.just(new Session(USERTYPE, JwtTokenUtil.createJWT(ID, subject), WALLET_ID));
    }

    public Mono Login(String USERNAME, String PASSWORD)
    {
        Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("USERNAME").is(USERNAME),
                                Criteria.where("PASSWORD").is(PASSWORD)
                        ));

        // query.fields().include("USERNAME");


        return MongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{

                    return newSession(output.getUSERTYPE(),
                            output.getPRIMARY_KEY(), String.join(",",output.getUSERTYPE()), output.getWALLET_ID());

                })
                .switchIfEmpty(loginErrorMessage());





    }

}
