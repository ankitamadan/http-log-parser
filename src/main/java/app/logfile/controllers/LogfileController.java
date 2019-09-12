package app.logfile.controllers;

import app.logfile.service.LoggerContentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class LogfileController {

    @Autowired
    private LoggerContentService loggerContentService;


    @ApiOperation(value = "View a list of top 3 most visited URLs", response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list")})
    @GetMapping("/topThreeVisitedUrls")
	public @ResponseBody String top3VisitedUrls() throws IOException {
		return loggerContentService.top3VisitedUrls();
	}

    @ApiOperation(value = "View a list of unique IP addresses", response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list")})
    @GetMapping("/uniqueIPAddresses")
    public @ResponseBody String uniqueIPAddresses() throws IOException {
        return loggerContentService.uniqueIPAddresses();
    }

    @ApiOperation(value = "View a list of top 3 most active IP addresses", response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list")})
    @GetMapping("/topThreeMostActiveIPAddresses")
    public @ResponseBody ResponseEntity<String> topThreeMostActiveIPAddresses() throws IOException {
        return ResponseEntity.ok().body(loggerContentService.top3MostActiveIpAddresses());
    }
}
