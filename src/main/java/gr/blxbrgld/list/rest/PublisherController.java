package gr.blxbrgld.list.rest;

import gr.blxbrgld.list.utils.Tags;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Publisher Controller
 * @author blxbrgld
 */
@RestController
@Validated
@RequestMapping("/publisher")
@Api(description = "Reference data related operations", tags = Tags.REFERENCE_DATA)
public class PublisherController {

}
