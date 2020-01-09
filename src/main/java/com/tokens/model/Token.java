package com.tokens.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Token
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-18T15:17:56.845-07:00")

public class Token   {
  @JsonProperty("tokenKey")
  private String tokenKey = null;

  @JsonProperty("tokenValue")
  private String tokenValue = null;

  public Token tokenKey(String tokenKey) {
    this.tokenKey = tokenKey;
    return this;
  }

  /**
   * Get tokenKey
   * @return tokenKey
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getTokenKey() {
    return tokenKey;
  }

  public void setTokenKey(String tokenKey) {
    this.tokenKey = tokenKey;
  }

  public Token tokenValue(String tokenValue) {
    this.tokenValue = tokenValue;
    return this;
  }

  /**
   * Get tokenValue
   * @return tokenValue
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getTokenValue() {
    return tokenValue;
  }

  public void setTokenValue(String tokenValue) {
    this.tokenValue = tokenValue;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Token token = (Token) o;
    return Objects.equals(this.tokenKey, token.tokenKey) &&
        Objects.equals(this.tokenValue, token.tokenValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tokenKey, tokenValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Token {\n");
    
    sb.append("    tokenKey: ").append(toIndentedString(tokenKey)).append("\n");
    sb.append("    tokenValue: ").append(toIndentedString(tokenValue)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

