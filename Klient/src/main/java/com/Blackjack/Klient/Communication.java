package com.Blackjack.Klient;

import com.Blackjack.Klient.Dto.BaseResponse;
import com.Blackjack.Klient.Dto.GameResponse;
import com.Blackjack.Klient.Dto.MoveResponse;
import com.Blackjack.Klient.Dto.SummaryGameResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class Communication {
    private static final ObjectMapper mapper = new ObjectMapper();


    public static <T> void send(T message, Socket socket) throws IOException {
        var buffer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        buffer.write(mapper.writeValueAsString(message));
        buffer.newLine();
        buffer.flush();
    }

    public static BaseResponse receiveBaseResponse(Socket socket) throws IOException {
        return mapper.readValue(receive(socket), BaseResponse.class);
    }

    public static GameResponse receiveGameResponse(Socket socket) throws IOException {
        return mapper.readValue(receive(socket), GameResponse.class);
    }

    public static MoveResponse receiveMoveResponse(Socket socket) throws IOException {
        return mapper.readValue(receive(socket), MoveResponse.class);
    }

    public static SummaryGameResponse receiveSummaryGameResponse(Socket socket) throws IOException {
        return mapper.readValue(receive(socket), SummaryGameResponse.class);
    }

    private static String receive(Socket socket) throws IOException {
        var buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return buffer.readLine();
    }
}
