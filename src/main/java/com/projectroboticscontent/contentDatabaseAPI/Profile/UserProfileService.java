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
public class UserProfileService {

    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;

    public UserProfileService() {
        this.registrationClient = MongoClients.create();
        this.registrationMongoOps = new ReactiveMongoTemplate(registrationClient, "testLoginDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Profile Error\n");
    }

    public Mono submissionError(){

        return Mono.just(createStatusException());

    }

    public Mono Error(){

        return Mono.just("error");

    }

    public Mono checkProfile(String token)
    {
        final long ID = JwtTokenUtil.parseJWTProfile(token);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .switchIfEmpty(Error());


    }
}
