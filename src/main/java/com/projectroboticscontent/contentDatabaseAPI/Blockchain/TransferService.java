package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.TransactionHistory.AddTransactionServiceStatic;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class TransferService {


    private MongoClient registrationClient;
    private ReactiveMongoTemplate registrationMongoOps;


    public TransferService() {
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

    public Mono checkWalletID(String token, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));

        //  query.fields().include("WALLET_ID");
        //  query.fields().include("EMAIL");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .switchIfEmpty(Error());


    }

    private Mono checkOtherWalletID(String token, String USERTYPE, String EMAIL)
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

                            return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                                    .switchIfEmpty(Error());
                        }
                );
    }

    private Mono SendCoin(String WALLET_ID, String RECEIVER, String AMOUNT)
    {
        WebClient webClient = WebClient.create();
        String url = Configuration.blockchainIPAddress+"/sol/transfer";

        return webClient
                .get()
                .uri(url+"?address="+WALLET_ID
                        +"&receiver="+RECEIVER
                        +"&amount="+AMOUNT)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono SendCoin2(String token, String USERTYPE, String RECEIVER, String AMOUNT)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));

        //  query.fields().include("WALLET_ID");
        //  query.fields().include("EMAIL");


        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{
                    return SendCoin(output.getWALLET_ID(),  RECEIVER,  AMOUNT);
                })
                .flatMap(
                        output->{
                            return AddTransactionServiceStatic.AddTransaction4(token, USERTYPE, RECEIVER,
                                    Long.valueOf(AMOUNT), 1);
                        }
                )
                .switchIfEmpty(Error());

    }

    public Mono SendCoinWithEmail(String token, String USERTYPE, String EMAIL, String AMOUNT)
    {
        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("EMAIL").is(EMAIL)

                        ));

        return registrationMongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output->{
                    return SendCoin2( token,  USERTYPE, output.getWALLET_ID(),  AMOUNT);
                })
                .switchIfEmpty(Error());

    }



}
