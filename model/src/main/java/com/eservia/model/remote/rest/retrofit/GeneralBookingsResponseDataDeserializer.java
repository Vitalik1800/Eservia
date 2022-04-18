package com.eservia.model.remote.rest.retrofit;

import com.eservia.model.entity.BeautyBooking;
import com.eservia.model.entity.BookingKindEnum;
import com.eservia.model.entity.GeneralBooking;
import com.eservia.model.entity.RestoBooking;
import com.eservia.model.entity.RestoDelivery;
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class GeneralBookingsResponseDataDeserializer implements JsonDeserializer<GeneralBookingsResponseData> {

    @Override
    public GeneralBookingsResponseData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        JsonElement kindJsonElement = jsonObject.get("kind");
        JsonObject bodyJsonObject = jsonObject.getAsJsonObject("body");

        if (kindJsonElement == null || bodyJsonObject == null) {
            return null;
        }

        Integer kind = kindJsonElement.getAsInt();
        GeneralBookingsResponseData data = new GeneralBookingsResponseData();
        data.setKind(kind);
        data.setBody(deserializeBody(kind, bodyJsonObject, context));
        return data;
    }

    private GeneralBooking deserializeBody(Integer kind, JsonObject body, JsonDeserializationContext context) {
        switch (kind) {
            case BookingKindEnum.BOOKING:
                return context.deserialize(body, RestoBooking.class);
            case BookingKindEnum.DELIVERY:
                return context.deserialize(body, RestoDelivery.class);
            case BookingKindEnum.BEAUTY:
            case BookingKindEnum.HEALTH:
                return context.deserialize(body, BeautyBooking.class);
        }
        return null;
    }
}
