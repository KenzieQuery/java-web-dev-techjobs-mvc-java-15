package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.data.JobData;
import org.launchcode.techjobsmvc.models.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

//provides functionality for users to see either a table showing all the options for the different
// Job fields (employer, location, coreCompetency, and positionType) or a list of details for a selected set of jobs.
@Controller
//constructor that populates columnChoices and tableChoices with values.
@RequestMapping(value = "list")
public class ListController {

    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, Object> tableChoices = new HashMap<>();

    // At /list, you’ll see an “All” column in the table. However, this option doesn’t work yet,
    // and you will fully implement that view as you work on the assignment.
    public ListController () {
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");

        //tableChoices.put("all", );
        tableChoices.put("employer", JobData.getAllEmployers());
        tableChoices.put("location", JobData.getAllLocations());
        tableChoices.put("positionType", JobData.getAllPositionTypes());
        tableChoices.put("coreCompetency", JobData.getAllCoreCompetency());

    }

    @RequestMapping(value = "")
    public String list(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("tableChoices", tableChoices);
        model.addAttribute("employers", JobData.getAllEmployers());
        model.addAttribute("locations", JobData.getAllLocations());
        model.addAttribute("positions", JobData.getAllPositionTypes());
        model.addAttribute("skills", JobData.getAllCoreCompetency());

        return "list";
    }

    // list and listJobsByColumnAndValue handler methods, with routes as annotated above their definitions.
    // The first method renders a view that displays a table of clickable links for the different job categories.
    // The second method renders a different view that displays information for the jobs that relate to a
    // selected category. Both of the handlers obtain data by implementing the JobData class methods.
    //user will arrive at this handler method as a result of clicking on a link within the list view,
    // rather than via submitting a form.
    //** note that the listJobsByColumnAndValue method deals with an “all” scenario differently than if a user
    // clicks one of the category links.**
    @RequestMapping(value = "jobs")
    //controller uses two query parameters passed in as column and value to determine what to fetch from JobData.
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        ArrayList<Job> jobs;
        // In the case of "all" it will fetch all job data,
        if (column.toLowerCase().equals("all")){
            jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");
        }
        //otherwise, it will retrieve a smaller set of information.
        else {
            jobs = JobData.findByColumnAndValue(column, value);
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("jobs", jobs);
        // The controller then renders the list-jobs.html view.
        return "list-jobs";
    }
}
