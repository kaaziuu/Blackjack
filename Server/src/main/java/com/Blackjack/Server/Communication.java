package com.Blackjack.Server;

import com.Blackjack.Server.DTO.BaseRequest;
import com.Blackjack.Server.DTO.BaseResponse;
import com.Blackjack.Server.DTO.JoinRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Communication {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> void send(T message, Socket socket) {
        try {
            var buffer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            buffer.write(mapper.writeValueAsString(message));
            buffer.newLine();
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JoinRequest receiveJoinRequest(Socket socket) throws IOException {
        return mapper.readValue(receive(socket), JoinRequest.class);
    }

    public static BaseRequest receiveBaseRequest(Socket socket) throws IOException {
        return mapper.readValue(receive(socket), BaseRequest.class);
    }

    private static String receive(Socket socket) throws IOException {
        var buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return buffer.readLine();
    }
}
