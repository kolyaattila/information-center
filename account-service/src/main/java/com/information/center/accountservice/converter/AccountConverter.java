package com.information.center.accountservice.converter;

import com.information.center.accountservice.entity.AccountEntity;
import com.information.center.accountservice.model.AccountRequest;
import com.information.center.accountservice.model.CreateAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring")
public interface AccountConverter {

  @Mapping(target = "photo", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, expression = "java(createAccountRequest.getPhoto() != null ?  createAccountRequest.getPhoto().getBytes() : null)")
  AccountEntity toAccount(CreateAccountRequest createAccountRequest);

  @Mapping(target = "photo", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, expression = "java(new String(account.getPhoto() == null ? \"\".getBytes() : account.getPhoto()  ))")
  AccountRequest toAccountRequest(AccountEntity account);

  @Mapping(target = "photo", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, expression = "java(accountRequest.getPhoto() != null ?  accountRequest.getPhoto().getBytes() : null)")
  AccountEntity toAccount(AccountRequest accountRequest);

}
