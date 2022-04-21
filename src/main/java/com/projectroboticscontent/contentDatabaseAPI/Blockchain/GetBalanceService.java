package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GetBalanceService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;


    public GetBalanceService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testLoginDatabase");

    }

    public Mono<String> getBalance(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final String url = Configuration.blockchainIPAddress + "/sol/get-balance";

        WebClient balanceClient = WebClient.create();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));


        return MongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{

                    return balanceClient
                            .get()
                            .uri(url+"?address="+output.getWALLET_ID())
                            .retrieve()
                            .bodyToMono(String.class);

                        }
                )
                .switchIfEmpty(Mono.just("error"));


    }


}
