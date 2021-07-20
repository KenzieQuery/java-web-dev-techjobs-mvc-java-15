package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.data.JobData;
import org.launchcode.techjobsmvc.models.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;
//Currently, the search controller contains only a single method, search. It simply renders the form defined
// in the search.html template.
//Later in this assignment, you will receive instructions for adding a second handler to deal with user input
// and display the search results.
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    //configure the correct mapping type and mapping route, refer to the form tag in the search.html template.
    @RequestMapping(value = "results")
    // The displaySearchResults method should take in a Model parameter.
    // two other parameters, specifying the type of search and the search term. name them appropriately, based on the
    // corresponding form field names defined in search.html.
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        // store the results in a jobs ArrayList.
        ArrayList<Job> jobs;
        jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        model.addAttribute("type", searchType);
        model.addAttribute("columns", columnChoices);
        //Pass ListController.columnChoices into the view, as the existing search handler does.
        model.addAttribute("title", "Search Condition: " + columnChoices.get(searchType) + " Search Term: " + searchTerm);
        // Pass jobs into the search.html view via the model parameter.
        model.addAttribute("jobs", jobs);
        return "search.html";
    }

}
