package com.projectroboticscontent.contentDatabaseAPI.Profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;


    public LoginService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testLoginDatabase");

    }

    /*
    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login Error, invalid username and password\n");
    }

    public ResponseStatusException createSuccess(Session session)
    {

        //ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        //String sessionInfo = mapper.writeValueAsString(mapper);

        return
                new ResponseStatusException(HttpStatus.OK, session.getTOKEN());
    }

    */

    /*
    public Mono<ResponseStatusException> loginError(){

        return Mono.just(createStatusException());

    }

    */

    /*
    public Mono<ResponseStatusException> loginSuccess(String[] USERTYPE, long ID, String subject, String WALLET_ID){

       return newSession(USERTYPE,  ID, subject, WALLET_ID)
                .flatMap((output)->{
                    //try {
                        return Mono.just(createSuccess(output));
                    //}// catch (JsonProcessingException e) {
                     //   return loginError();
                   // }
                });


    }

    */



    /*
    public Mono<String> loginErrorMessage(){

        return Mono.just("Login error. Check username and password.");

    }

    */

    public Mono<Session> newSession(String[] USERTYPE, long ID, String subject, String WALLET_ID,
                                    HttpStatus SERVER_STATUS, String SERVER_STATUS_MESSAGE)
    {
        return Mono.just(new Session(USERTYPE, JwtTokenUtil.createJWT(ID, subject), WALLET_ID,
                SERVER_STATUS, SERVER_STATUS_MESSAGE));
    }

    public Mono<Session> Login(String USERNAME, String PASSWORD)
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
                            output.getPRIMARY_KEY(), String.join(",",output.getUSERTYPE()),
                            output.getWALLET_ID(),HttpStatus.OK,"Login Success");

                            //return loginSuccess(output.getUSERTYPE(),
                            //output.getPRIMARY_KEY(), String.join(",",output.getUSERTYPE()), output.getWALLET_ID());

                })
                .switchIfEmpty(Mono.just(new Session(new String[]{}, "", "",
                        HttpStatus.INTERNAL_SERVER_ERROR, "Login Error. Check Username and Password")));



    }

}
