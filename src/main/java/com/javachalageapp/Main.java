package com.javachalageapp;

import com.javachalageapp.dto.ApiResponseDto;
import com.javachalageapp.dto.mapper.TagMapper;
import com.javachalageapp.dto.mapper.UserMapper;
import com.javachalageapp.model.User;
import com.javachalageapp.service.HttpClient;
import com.javachalageapp.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        UserMapper mapper = new UserMapper();
        TagMapper tagMapper = new TagMapper();
        UserServiceImpl service = new UserServiceImpl(client, mapper, tagMapper);
        service.printUsers();
        ApiResponseDto apiResponseDto = client.get("https://api.stackexchange.com/2.3/users?site=stackoverflow&filter=!6Wfm_gS.goZzc",
                ApiResponseDto.class);
        User user = mapper.parseApiResponseDto(apiResponseDto.getItems()[0]);
        System.out.println("Done");
    }
}
