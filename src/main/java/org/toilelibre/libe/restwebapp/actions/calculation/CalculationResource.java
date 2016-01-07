package org.toilelibre.libe.restwebapp.actions.calculation;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.toilelibre.libe.restwebapp.actions.LinkHelper;
import org.toilelibre.libe.restwebapp.actions.Response;
import org.toilelibre.libe.restwebapp.actions.entity.Node;
import org.toilelibre.libe.restwebapp.actions.entity.NodeFactory;
import org.toilelibre.libe.restwebapp.actions.entity.ObjectNode;
import org.toilelibre.libe.restwebapp.ioc.webapp.argresolver.RequestBodyPath;
import org.toilelibre.libe.restwebapp.model.calculation.Calculator;

@RestController
@RequestMapping (value = "/calculation")
public class CalculationResource {
    private static Logger LOGGER = LoggerFactory.getLogger (CalculationResource.class);

    @Inject
    private LinkHelper linkHelper;

    @Inject
    private Calculator calculator;

    @RequestMapping (value = "/", method = RequestMethod.POST)
    public Response<ObjectNode<Node>> calculate (final @RequestBodyPath ("int1") int int1, final @RequestBodyPath ("int2") int int2) throws WrongCalculationAnswerException {
        CalculationResource.LOGGER.info (String.format ("Calculating %d + %d", int1, int2));

        final NodeFactory factory = NodeFactory.instance;
        final int result = this.calculator.sum (int1, int2);
        // because of a bug in the jackson lib, cannot parse the integers
        return new Response<ObjectNode<Node>> (this.linkHelper.get (), this.linkHelper.surroundWithLinks (factory.objectNode () 
                .put ("int1", int1).put ("int2", int2).put ("result", result)));
    }

    @RequestMapping (value = "/guess", method = RequestMethod.POST)
    public Response<Integer> guessSum (final @RequestBodyPath ("int1") int int1, final @RequestBodyPath ("int2") int int2, final @RequestBodyPath ("result") int result)
            throws WrongCalculationAnswerException {

        CalculationResource.LOGGER.info (String.format ("Checking if %d + %d = %d", int1, int2, result));
        final int myResult = this.calculator.sum (int1, int2);

        if (myResult != result) {
            throw new WrongCalculationAnswerException ();
        }

        return new Response<Integer> (this.linkHelper.get (), myResult);
    }
}
