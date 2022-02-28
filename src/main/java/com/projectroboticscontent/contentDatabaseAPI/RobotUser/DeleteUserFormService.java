package com.projectroboticscontent.contentDatabaseAPI.RobotUser;

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
public class DeleteUserFormService {

    private MongoClient client;
    private ReactiveMongoTemplate MongoOps;



    public DeleteUserFormService() {

        this.client = MongoClients.create();
        this.MongoOps = new ReactiveMongoTemplate(client, "testUserDatabase");


    }

    public Mono deletionMessage(){
        return Mono.just("Form was successfully deleted");
    }

    public ResponseStatusException createStatusException(){
        return
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error\n");
    }

    public Mono Error(){

        return Mono.just(createStatusException());

    }

    public Mono deleteForm(String token, long FORM_ID, String USERTYPE)
    {
        final long ID = JwtTokenUtil.parseJWT(token,USERTYPE);
        final long formID = FORM_ID;

        final Query query =
                Query.query(
                        new Criteria().andOperator(
                                Criteria.where("PRIMARY_KEY").is(ID),
                                Criteria.where("FORM_ID").is(formID)
                        ));

        //query.fields().include("FORM_ID");
        //query.fields().include("JSON_FORM_DATA");
        //query.fields().include("FORM_TITLE");



        return MongoOps.findAndRemove(query, FormSubmission.class,"UserRequestCollection")
                .flatMap(output -> {return deletionMessage();})
                .switchIfEmpty(Error());


    }


}
