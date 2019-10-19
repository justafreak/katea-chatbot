package ml.strikers.kateaserver.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analysis")
public class ReviewAnalysisController {

    private final ReviewAnalysisService reviewAnalysisService;

    public ReviewAnalysisController(ReviewAnalysisService reviewAnalysisService) {
        this.reviewAnalysisService = reviewAnalysisService;
    }


    @GetMapping("/reviews")
    public ResponseEntity<String> responseEntity() {
        try {
            String responseMessage = reviewAnalysisService.startReviewAnalysis();
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
