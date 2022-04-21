package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class GetWalletAddressService {


    private final MongoClient registrationClient;
    private final ReactiveMongoTemplate registrationMongoOps;

    public GetWalletAddressService() {
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

      //  query.fields().include("WALLET_ID");
      //  query.fields().include("EMAIL");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection");
               // .switchIfEmpty(Error());


    }

    private Mono<UserProfile> checkOtherWalletID(String token, String USERTYPE, String EMAIL)
    {

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("EMAIL").is(EMAIL)

                        ));

      //  query.fields().include("WALLET_ID");
      //  query.fields().include("EMAIL");

        return checkWalletID(token,USERTYPE)
                .flatMap(

                        output->{

                            return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection");
                                    //.switchIfEmpty(Error());
                        }
                );
    }


}
