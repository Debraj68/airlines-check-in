package com.myapp.spring.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.myapp.spring.model.User1;
import com.myapp.spring.repository.User1Repository;



@RestController
@RequestMapping("/api/v1/users")
public class User1API {
	
  @Autowired	
  public User1Repository repository;	
  @GetMapping
  public ResponseEntity<List<User1>> findAll(){
	  return new ResponseEntity<List<User1>>(repository.findAll(),HttpStatus.OK);
  }
  @GetMapping("search/{pnr}")
  public ResponseEntity<List<User1>> findUserByPnr(@PathVariable("pnr")Integer pnr){
	  return new ResponseEntity<List<User1>>(repository.findByPnr(pnr).get(),HttpStatus.OK);
  }
  @PostMapping	  
  public ResponseEntity<User1> addNewUser(@RequestBody User1 user){
	  return new ResponseEntity<>(repository.save(user), HttpStatus.CREATED);	  
	  
  }
//http://localhost:8080/api/v1/users/1
@GetMapping("/{email}")
public ResponseEntity<User1> findByEmail(@PathVariable("email") String email){

return new ResponseEntity<User1>((User1) repository.findByEmail(email).get(), HttpStatus.OK);
}



@PutMapping("/{email}")
public ResponseEntity<User1> updateUser(@PathVariable("email") String email,
@RequestBody User1 user) throws ResourceNotFoundException{
User1 existingUser = (User1) repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException
      ("User not found for this email :: " + email));

BeanUtils.copyProperties(user, existingUser);

return new ResponseEntity<User1>(repository.save(existingUser), 
HttpStatus.CREATED);
}


//@DeleteMapping("/{email}")
//public Map<String, Boolean> delete(@PathVariable(value = "email") String email)
// throws ResourceNotFoundException {
//   User1 user = (User1) repository.findByEmail(email)
//      .orElseThrow(() -> new ResourceNotFoundException
//      ("User not found for this email :: " + email));
//
//
//   repository.delete(user);
//    Map<String, Boolean> response = new HashMap<>();
//    response.put("deleted", Boolean.TRUE);
//    return response;
//}
@GetMapping("/findByBaggageWeight/{baggageWeight}")
public ResponseEntity<List<User1>> findUsersByBaggageWeight
(@PathVariable("baggageWeight") Integer baggageWeight){

return new ResponseEntity<List<User1>>
(repository.findByBaggageWeightGreaterThanEqual(baggageWeight).get(), HttpStatus.OK);
}
// http://localhost:8080/api/v1/products/findByPriceOrName?price=
@GetMapping("/findByPnrOrEmail")
public ResponseEntity<List<User1>> findProductsByPnrOrEmail
(@RequestParam("pnr") Optional<Integer> pnr,
@RequestParam("email") Optional<String> email){
return new ResponseEntity<List<User1>>
(repository.findByPnrOrEmail(pnr.orElse(0), email.orElse("")).get(), HttpStatus.OK);
}

}
