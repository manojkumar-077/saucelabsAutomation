package com.assignment.data;
import java.util.Map;

public final class Users {
 private Users() {}
 public record User(String username, String password) {}
 public static final String PASSWORD="secret_sauce";
 public static final Map<String,User> ALL=Map.of(
  "standard",new User("standard_user",PASSWORD), "locked",new User("locked_out_user",PASSWORD),
  "problem",new User("problem_user",PASSWORD), "glitch",new User("performance_glitch_user",PASSWORD),
  "error",new User("error_user",PASSWORD), "visual",new User("visual_user",PASSWORD),
  "invalid",new User("invalid_user","wrong_password"), "badUser",new User("wrong_user",PASSWORD),
  "badPass",new User("standard_user","wrong_password"));
 public static User profile(String key) {
  return ALL.getOrDefault(key, ALL.get("standard"));
 }
}
