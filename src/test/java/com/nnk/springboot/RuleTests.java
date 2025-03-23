//package com.nnk.springboot;
//
//import com.nnk.springboot.domain.Rule;
//import com.nnk.springboot.repositories.RuleNameRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RuleTests {
//
//    @Autowired
//    private RuleNameRepository ruleNameRepository;
//
//    @Test
//    public void ruleTest() {
//        Rule rule = new Rule("RuleName Name", "Description", "Json", "Template", "SQL", "SQL Part");
//
//        // Save
//        rule = ruleNameRepository.save(rule);
//        Assert.assertNotNull(rule.getId());
//        Assert.assertTrue(rule.getName().equals("RuleName Name"));
//
//        // Update
//        rule.setName("RuleName Name Update");
//        rule = ruleNameRepository.save(rule);
//        Assert.assertTrue(rule.getName().equals("RuleName Name Update"));
//
//        // Find
//        List<Rule> listResult = ruleNameRepository.findAll();
//        Assert.assertTrue(listResult.size() > 0);
//
//        // Delete
//        Integer id = rule.getId();
//        ruleNameRepository.delete(rule);
//        Optional<Rule> ruleList = ruleNameRepository.findById(id);
//        Assert.assertFalse(ruleList.isPresent());
//    }
//}
