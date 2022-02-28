package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.Submission;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DepositService {

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;


    public DepositService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testLoginDatabase");

    }

    public Mono deposit(String token, String USERTYPE, String AMOUNT)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final String url = Configuration.blockchainIPAddress + "/sol/deposit";

        WebClient depositClient = WebClient.create();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));


        return MongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{

                            return depositClient
                                    .get()
                                    .uri(url+"?address="+output.getWALLET_ID()+"&amount=" + AMOUNT)
                                    .retrieve()
                                    .bodyToMono(String.class);

                        }
                );


    }






}
