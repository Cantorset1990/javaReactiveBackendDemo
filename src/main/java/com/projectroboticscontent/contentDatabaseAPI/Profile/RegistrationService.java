package com.projectroboticscontent.contentDatabaseAPI.Profile;

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


    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;
  //  private String connectionURL =
    //        "mongodb+srv://user:12345@cluster0.qfbam.mongodb.net/testLoginDatabase?retryWrites=true&w=majority";

    public RegistrationService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testLoginDatabase");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration Error, username and password in use\n");
    }

    public Mono registrationError(){

        return Mono.just(createStatusException());

    }

    public Mono newSession(String[] USERTYPE, long ID, String subject, String WALLET_ID)
    {
        return Mono.just(new Session(USERTYPE, JwtTokenUtil.createJWT(ID, subject), WALLET_ID));
    }


    public Mono Registration(String FIRSTNAME, String LASTNAME, String USERNAME, String PASSWORD,
                             String EMAIL, String PHONE, String[] USERTYPE, String WALLET_ID)
    {


        final UserProfile user = new UserProfile(FIRSTNAME, LASTNAME, USERNAME,
                PASSWORD, EMAIL, PHONE, USERTYPE, new long[]{}, WALLET_ID);


        WebClient depositWebClient = WebClient.create();
        String depositURL = Configuration.blockchainIPAddress + "/sol/deposit";


        return MongoOps.insert(user,"RegistrationCollection")

                /*
                .flatMap (output -> {
                            return depositWebClient
                                    .get()
                                    .uri(depositURL+"?address="+output.getWALLET_ID()+"&amount=" +"1")
                                    .retrieve()
                                    .bodyToMono(String.class);
                        }
                )

                */


                .flatMap(output->{
                    return newSession(output.getUSERTYPE(), output.getPRIMARY_KEY(),
                            String.join(",",output.getUSERTYPE()), output.getWALLET_ID()) ;
                });

                //Deposit 100 000 tokens on successful registration

              //  .then(newSession(user.getUSERTYPE(), user.getPRIMARY_KEY(), String.join(",",user.getUSERTYPE())));

    }

    public String Parser(String stringToParse) {
       // JSONObject JSONObject = new JSONObject(json);
       // return JSONObject.toString();


        //JSONParser parser = new JSONParser();
        //JSONObject json = (JSONObject) parser.parse(stringToParse);
        //Gson g = new Gson();
        //return g.fromJson(json, String.class);
        //json.get
        return stringToParse.substring(1, stringToParse.length()-2);
        //return "";
    }


    public Mono Registration2(String FIRSTNAME, String LASTNAME, String USERNAME, String PASSWORD,
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




    public Mono newRegistration(String FIRSTNAME, String LASTNAME, String USERNAME,
                                String PASSWORD, String EMAIL, String PHONE, String[] USERTYPE)
    {
        Query query =
                Query.query(
                        new Criteria().orOperator(
                                Criteria.where("USERNAME").is(USERNAME),
                                Criteria.where("EMAIL").is(EMAIL)
                        ));

        // query.fields().include("USERNAME");
        // query.fields().include("EMAIL");
        // query.fields().include("token");
        // query.fields().include("WALLET_ID");

        //query.fields().exclude("PRIMARY_KEY", "PASSWORD");


        return MongoOps.findOne(query, UserProfile.class,"RegistrationCollection")
                .flatMap(output ->{
                    return registrationError();
                })

                .switchIfEmpty(Registration2(FIRSTNAME,LASTNAME,USERNAME,PASSWORD,EMAIL, PHONE, USERTYPE));
               // .switchIfEmpty(Registration(FIRSTNAME,LASTNAME,USERNAME,PASSWORD,EMAIL, PHONE, USERTYPE, ""));




    }


}
