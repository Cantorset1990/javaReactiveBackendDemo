package com.projectroboticscontent.contentDatabaseAPI.Profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
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
public class RegistrationService {

    private final MongoClient client;
    private final ReactiveMongoTemplate MongoOps;

    public RegistrationService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testLoginDatabase");
    }

    /*
    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration Error, username and password in use\n");
    }

    */

    /*
    public Mono<ResponseStatusException> registrationError(){

        return Mono.just(createStatusException());

    }

    public Mono<String> registrationErrorString(){

        return Mono.just("Registration Error");

    }

    */

    /*
    public Mono<String> registrationSuccessString(Session session) throws JsonProcessingException {

          ObjectMapper mapper = new ObjectMapper();
          mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
          String sessionInfo = mapper.writeValueAsString(mapper);
          return Mono.just(sessionInfo);

    }

    public ResponseStatusException createSuccessException( Session session)
            {

      //  ObjectMapper mapper = new ObjectMapper();
      //  mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
      //  String sessionInfo = mapper.writeValueAsString(mapper);

        return
                new ResponseStatusException(HttpStatus.OK, session.getTOKEN());
    }

    */



    /*
    public Mono<Session> registrationSuccess(String FIRSTNAME, String LASTNAME, String USERNAME,
                                               String PASSWORD, String EMAIL,
                                               String PHONE, String[] USERTYPE){

        return Registration2( FIRSTNAME,  LASTNAME,  USERNAME,
                 PASSWORD,  EMAIL,  PHONE,  USERTYPE);
                /*
                .flatMap(output->{

            try {
                return registrationSuccessString(output);
            } catch (JsonProcessingException e) {
               return registrationErrorString();
            }

        });




    }

    */




    public Mono<Session> newSession(String[] USERTYPE, long ID, String subject, String WALLET_ID, HttpStatus SERVER_STATUS,
                                    String SERVER_STATUS_MESSAGE)
    {
        return Mono.just(new Session(USERTYPE, JwtTokenUtil.createJWT(ID, subject), WALLET_ID, SERVER_STATUS, SERVER_STATUS_MESSAGE));
    }


    public Mono<Session> Registration(String FIRSTNAME, String LASTNAME, String USERNAME, String PASSWORD,
                             String EMAIL, String PHONE, String[] USERTYPE, String WALLET_ID)
    {


        final UserProfile user = new UserProfile(FIRSTNAME, LASTNAME, USERNAME,
                PASSWORD, EMAIL, PHONE, USERTYPE, new long[]{}, WALLET_ID);


        WebClient depositWebClient = WebClient.create();
        String depositURL = Configuration.blockchainIPAddress + "/sol/deposit";


        return MongoOps.insert(user,"RegistrationCollection")


                .flatMap(output->{
                    return newSession(output.getUSERTYPE(), output.getPRIMARY_KEY(),
                            String.join(",",output.getUSERTYPE()), output.getWALLET_ID(),
                            HttpStatus.OK, "Registration Success") ;
                });



    }

    public String Parser(String stringToParse) {

        return stringToParse.substring(1, stringToParse.length()-2);

    }


    public Mono<Session> Registration2(String FIRSTNAME, String LASTNAME, String USERNAME, String PASSWORD,
                                                   String EMAIL, String PHONE, String[] USERTYPE)
    {
        WebClient webClient = WebClient.create();
        String url = Configuration.blockchainIPAddress + "/sol/create-wallet";


        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(output->{
                    return Registration( FIRSTNAME,  LASTNAME,  USERNAME,  PASSWORD,
                            EMAIL,  PHONE, USERTYPE, Parser(output));
                });


    }




    public Mono<Session> newRegistration (String FIRSTNAME, String LASTNAME, String USERNAME,
                                           String PASSWORD, String EMAIL, String PHONE, String[] USERTYPE) {
        Query query =
                Query.query(
                        new Criteria().orOperator(
                                Criteria.where("USERNAME").is(USERNAME),
                                Criteria.where("EMAIL").is(EMAIL)
                        ));



          return MongoOps.findOne(query, UserProfile.class, "RegistrationCollection")
                  .flatMap((output)->{
                      //throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration Error, username and password in use");
                      return Mono.just(new Session(new String[]{}, "", "",
                              HttpStatus.INTERNAL_SERVER_ERROR, "Email or Username already exist"));
                  })
                  .switchIfEmpty(Registration2( FIRSTNAME,  LASTNAME,  USERNAME,
                          PASSWORD,  EMAIL,  PHONE,  USERTYPE));


    }


}
