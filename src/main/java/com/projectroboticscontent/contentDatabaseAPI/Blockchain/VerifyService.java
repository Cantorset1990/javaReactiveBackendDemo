package com.projectroboticscontent.contentDatabaseAPI.Blockchain;

import com.google.common.hash.Hashing;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.projectroboticscontent.contentDatabaseAPI.Investor.FinanceRequest;
import com.projectroboticscontent.contentDatabaseAPI.Profile.UserProfile;
import com.projectroboticscontent.contentDatabaseAPI.RobotUser.FormSubmission;
import com.projectroboticscontent.contentDatabaseAPI.SolutionProvider.SolutionInfo;
import com.projectroboticscontent.contentDatabaseAPI.TransactionHistory.HistoryInfo;
import com.projectroboticscontent.contentDatabaseAPI.Utility.Configuration;
import com.projectroboticscontent.contentDatabaseAPI.Utility.JwtTokenUtil;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Service
public class VerifyService {

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;

    private MongoClient historyClient ;
    private ReactiveMongoTemplate historyMongoOps;

    private MongoClient solutionClient;
    private ReactiveMongoTemplate solutionMongoOps;

    private MongoClient financeClient;
    private ReactiveMongoTemplate financeMongoOps;

    private MongoClient userClient;
    private ReactiveMongoTemplate userMongoOps;


    public VerifyService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testLoginDatabase");

        this.historyClient =  MongoClients.create();
        this.historyMongoOps  = new ReactiveMongoTemplate(historyClient,
                "testTransactionHistoryDatabase");

        this.solutionClient = MongoClients.create();
        this.solutionMongoOps = new ReactiveMongoTemplate(solutionClient,
                "testSolutionDatabase");

        this.financeClient = MongoClients.create();
        this.financeMongoOps = new ReactiveMongoTemplate(financeClient,
                "testInvestorDatabase");

        this.userClient = MongoClients.create();
        this.userMongoOps = new ReactiveMongoTemplate(userClient,
                "testUserDatabase");

    }

    public Mono verifyTransaction(String token, String USERTYPE, long TRANSACTION_ID)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final String ID_STRING = Long.toString(TRANSACTION_ID);

        final String url = Configuration.blockchainIPAddress + "/get" ;

        WebClient verifyClient = WebClient.create();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));

        final Query transactionQuery =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("TRANSACTION_ID").is(TRANSACTION_ID)

                        ));


        return MongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(out1->{
                    return historyMongoOps.findOne(transactionQuery, HistoryInfo.class,"HistoryCollection");
                })

                .flatMap(

                       out2->{
                           return Mono.just(out2.getRECEIVER_WALLET_ID() + out2.getSENDER_WALLET_ID()
                                   + out2.getRECEIVER_TRANSACTION_AMOUNT()
                                   + out2.getSENDER_TRANSACTION_AMOUNT() + out2.getNEW_RECEIVER_BALANCE() +
                                   out2.getNEW_SENDER_BALANCE() + TRANSACTION_ID
                                   + out2.getDATETIME() + out2.getPROPERTY());
                       }
                )

                .flatMap(out3->{

                            return verifyClient
                                    .get()
                                    .uri(url+"?key=" + ID_STRING)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .filter(getHash-> getHash == Hashing.sha256()
                                            .hashString(out3, StandardCharsets.UTF_8)
                                            .toString() )
                                    .switchIfEmpty(Mono.just("Hash does not match"));


                        }
                );


    }


    public Mono verifySolution(String token, String USERTYPE, long SOLUTION_ID)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final String ID_STRING = Long.toString(SOLUTION_ID);

        final String url = Configuration.blockchainIPAddress + "/get" ;

        WebClient verifyClient = WebClient.create();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));

        final Query solutionQuery =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("SOLUTION_ID").is(SOLUTION_ID)

                        ));


        return MongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(out1->{
                    return solutionMongoOps.findOne(solutionQuery, SolutionInfo.class,"SolutionProviderCollection");
                })

                .flatMap(

                        out2->{
                            return Mono.just( out2.getSOLUTION_OWNER() + out2.getSOLUTION_TITLE() + SOLUTION_ID
                                    + out2.getFORM_ID() + out2.getSOLUTION_PROVIDER_PRIMARY_KEY()
                                    + out2.getDATETIME()
                                    + out2.getSOLUTION_PROVIDER_WALLET_ID()
                                    + out2.getUSER_PRIMARY_KEY() + out2.getUSER_WALLET_ID()
                                    + out2.getJSON_SOLUTION_DATA());
                        }
                )

                .flatMap(out3->{

                            return verifyClient
                                    .get()
                                    .uri(url+"?key=" + ID_STRING)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .filter(getHash-> getHash == Hashing.sha256()
                                            .hashString(out3, StandardCharsets.UTF_8)
                                            .toString() )
                                    .switchIfEmpty(Mono.just("Hash does not match"));


                        }
                );


    }


    public Mono verifyFinance(String token, String USERTYPE, long FINANCE_REQUEST_ID)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final String ID_STRING = Long.toString(FINANCE_REQUEST_ID);

        final String url = Configuration.blockchainIPAddress + "/get" ;

        WebClient verifyClient = WebClient.create();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));

        final Query financeQuery =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("FINANCE_REQUEST_ID").is(FINANCE_REQUEST_ID)

                        ));


        return MongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(out1->{
                    return financeMongoOps.findOne(financeQuery, FinanceRequest.class,"FinanceRequestCollection");
                })

                .flatMap(

                        out2->{
                            return Mono.just(out2.getFINANCE_REQUEST_ID() + out2.getTOTAL_COST()
                                    + out2.getSOLUTION_TITLE()
                                    + out2.getPAYMENT() + out2.getUSAGE_TIME() +
                                    out2.getSOLUTION_PROVIDER_PRIMARY_KEY() + out2.getINVESTOR_PRIMARY_KEY()
                                    + out2.getDATETIME()
                                    +out2.getSOLUTION_PROVIDER_WALLET_ID() + out2.getINVESTOR_WALLET_ID()
                                    +out2.getHARDWARE_ID() + out2.getUSER_PRIMARY_KEY()
                                    + out2.getUSER_WALLET_ID() + out2.getSOLUTION_ID()
                                    +out2.getFINANCE_REQUEST_OWNER() + out2.getSOLUTION_OWNER());
                        }
                )

                .flatMap(out3->{

                            return verifyClient
                                    .get()
                                    .uri(url+"?key=" + ID_STRING)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .filter(getHash-> getHash == Hashing.sha256()
                                            .hashString(out3, StandardCharsets.UTF_8)
                                            .toString() )
                                    .switchIfEmpty(Mono.just("Hash does not match"));


                        }
                );


    }


    public Mono verifyForm(String token, String USERTYPE, long FORM_ID)
    {
        final long ID = JwtTokenUtil.parseJWT(token, USERTYPE);

        final String ID_STRING = Long.toString(FORM_ID);

        final String url = Configuration.blockchainIPAddress + "/get" ;

        WebClient verifyClient = WebClient.create();

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID)

                        ));

        final Query formQuery =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("FORM_ID").is(FORM_ID)

                        ));


        return MongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(out1->{
                    return financeMongoOps.findOne(formQuery, FormSubmission.class,"UserRequestCollection");
                })

                .flatMap(

                        out2->{
                            return Mono.just(out2.getUSERTYPE() + out2.getUSER_PRIMARY_KEY() + out2.getUSER_WALLET_ID()
                                    + out2.getFORM_TITLE() + out2.getJSON_FORM_DATA()
                                    + FORM_ID + out2.getDATETIME());
                        }
                )

                .flatMap(out3->{

                            return verifyClient
                                    .get()
                                    .uri(url+"?key=" + ID_STRING)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .filter(getHash-> getHash == Hashing.sha256()
                                            .hashString(out3, StandardCharsets.UTF_8)
                                            .toString() )
                                    .switchIfEmpty(Mono.just("Hash does not match"));


                        }
                );


    }


}
