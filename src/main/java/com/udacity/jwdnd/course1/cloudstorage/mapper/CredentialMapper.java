package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialsByUserId(Integer userId);


    @Insert("INSERT INTO CREDENTIALS(fileName) VALUES(#{fileName)")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(String url, String username, String password, Integer key, Integer userId);


    @Update("Update CREDENTIALS set url=#{url}, " +
            " username=#{username}, key=#{key},  password=#{password}  WHERE credentialId=#{credentialId}")
    int update(String url, String username, String password, Integer key, Integer userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int delete(@Param("credentialId") Integer credentialId);
}


