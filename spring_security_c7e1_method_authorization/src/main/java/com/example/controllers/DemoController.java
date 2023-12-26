package com.example.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {

    //@PreAuthorize -- rules applied before the method is called

    @GetMapping("/demo1")
    @PreAuthorize("hasAuthority('read')")
    public String demo(){
        return "Demo 1";
    }

    @GetMapping("/demo2")
    @PreAuthorize("hasAnyAuthority('read', 'write')")
    public String demo2(){
        return "Demo 2";
    }

    //authorizing based on the path variable
    /*@GetMapping("/demo3/{smth}")
    @PreAuthorize("#something == authentication.name") // authentication object is from security context
    public String demo3(@PathVariable("smth") String something){
        var a = SecurityContextHolder.getContext().getAuthentication();
        return "Demo 3";
    }*/

    //multiple authorization criteria
    @GetMapping("/demo3/{smth}")
    @PreAuthorize("(#something == authentication.name) and hasAnyAuthority('write')") //SpEL syntax, # to get path variable
    public String demo3(@PathVariable("smth") String something){
        var a = SecurityContextHolder.getContext().getAuthentication();
        return "Demo 3";
    }

    //using custom bean , recommend if we have so many criteria for cleaner code
    @GetMapping("/demo4")
    // you can pass parameters to condition()
    @PreAuthorize("@demo4ConditionEvaluator.condition()")  //SpEL: the bean should be with @ & first letter small.
    public String demo4(){
        return "Demo 4";
    }

    //@PostAuthorize - rule applied after the method is called , the call execute the method
    //but restrict the access to some returned value if the condition is not fulfilled.
    @GetMapping("/demo5")
    @PostAuthorize("returnObject == 'Demo 4'")  // based on the returnObject of the method
    public String demo5(){
        System.out.println("Hola"); //never use @PostAuthorize with methods that change data
        return "Demo 5";
    }
    //filters should be only used for security authorization related, not for business cases.
    //filter - don't restrict the access completely but restrict the access to some specific values
    //@PreFilter -- we need to have as a parameter an array or collection
    @GetMapping("/demo6")
    //filterObject is the value inside a collection in this case values, list of strings
    @PreFilter("filterObject.contains('a')") // this filter the value that are sent to the method
    public String demo6(@RequestBody List<String> values){
        System.out.println("values " + values); // values will be those who contains 'a'
        return "Demo 6";
    }

    //@PostFilter - filters the return value  --
    // -- so the return type must be an array or Collections.
    @GetMapping("/demo7")
    @PostFilter("filterObject.contains('a')")
    public List<String> demo7(){
        //this will not work b/c List.of creates immutable collection the aspect tries to change it
        //return List.of("abcd","bcdr","axyz","xyz");
        var list = new ArrayList<String>();
        list.add("abcd");
        list.add("bcdr");
        list.add("axyz");
        list.add("xyz");
        return list;

    }

}



/**
 * Method level authorization - both on non-web and web application.
 * Filter level authorization - applicable only on method level.
 * Theoretically, method level authorization are less permanent, since they use aspect to intercept.

 * why sometimes developers need method level authorization, is for clean code,
  i.e. not put so many rules on the security filter chain.
 just put who needs to be authenticated on security filter chain and put other expectation
  in terms of authorization using method level authorization.

 *what if there is a contradiction between the filter authorization and method level authorization
  -> since the filter level comes first the filter level authorization will be in action first.
   it is after the passing the filter it goes to the method authorization, starting from the controller.
 *
 *
 *
 * Note: @PreFilter and @PostFilter - security dependencies have to be used with security cases
   not with a business cases like filtering something.
   if it is business cases it should be on the service component.
 */
