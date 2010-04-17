package com.markupartist.nollbit.musicmachine.server.handlers;

import com.google.gson.Gson;
import com.markupartist.nollbit.musicmachine.server.MusicMachineApplication;
import com.markupartist.nollbit.musicmachine.server.MusicMachineHandler;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: johanm
 * Date: Apr 17, 2010
 * Time: 12:17:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatusHandler extends MusicMachineHandler {
    Gson gson = new Gson();

    @Override
    public String handleGet(Map<String, String> params) throws BadRequestException, InternalServerErrorException, ConflictException {
        return gson.toJson(MusicMachineApplication.playlist.getStatus());
    }
}