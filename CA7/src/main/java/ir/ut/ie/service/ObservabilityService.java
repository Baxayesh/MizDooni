package ir.ut.ie.service;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class ObservabilityService {

    String InstrumentationScopeName = "Mizdooni";

    LongCounter loginCounter;
    LongCounter reserveCounter;
    LongCounter canceledReservesCounter;

   LongCounter getLoginCounter(){
       if(loginCounter == null){
           loginCounter =
                   GlobalOpenTelemetry
                   .getMeter(InstrumentationScopeName)
                   .counterBuilder("successful-login")
                   .setDescription("Number of Successful Logins")
                   .build();
       }

       return loginCounter;
   }

   LongCounter getReserveCounter(){
       if(reserveCounter == null){
           reserveCounter =
                   GlobalOpenTelemetry
                   .getMeter(InstrumentationScopeName)
                   .counterBuilder("performed-reserves")
                   .setDescription("Number of Successful performed reserves")
                   .build();

       }

       return reserveCounter;
   }

   LongCounter getCanceledReservesCounter(){
       if(canceledReservesCounter == null){
           canceledReservesCounter = GlobalOpenTelemetry
                   .getMeter(InstrumentationScopeName)
                   .counterBuilder("canceled-reserves")
                   .setDescription("Number of canceled reserves")
                   .build();

       }

       return canceledReservesCounter;
   }


   public void addSuccessfulLogin(String method){
       getLoginCounter().add(1, Attributes.of(AttributeKey.stringKey("login method"), method));
   }

   public void addReserve(){
       getReserveCounter().add(1);
   }

   public void addCanceledReserve(){
       getCanceledReservesCounter().add(1);
   }
}
