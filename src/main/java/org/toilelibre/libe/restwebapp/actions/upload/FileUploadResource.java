package org.toilelibre.libe.restwebapp.actions.upload;

import javax.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.toilelibre.libe.restwebapp.actions.LinkHelper;
import org.toilelibre.libe.restwebapp.actions.Response;
import org.toilelibre.libe.restwebapp.actions.entity.Node;
import org.toilelibre.libe.restwebapp.actions.entity.NodeFactory;
import org.toilelibre.libe.restwebapp.actions.entity.ObjectNode;
import org.toilelibre.libe.restwebapp.ioc.webapp.argresolver.RequestFile;

@RestController
@RequestMapping (value = "/fileupload")
public class FileUploadResource {
    @Inject
    private LinkHelper linkHelper;
    
    @RequestMapping (value = "/", method = RequestMethod.POST)
    public Response<ObjectNode<Node>> upload (final @RequestFile ("file1") byte[] file1, final @RequestFile ("file2") byte[] file2) {
        final NodeFactory factory = NodeFactory.instance;
        return new Response<ObjectNode<Node>> (this.linkHelper.get (),
                this.linkHelper.surroundWithLinks (factory.objectNode ().put("ok", true)));
    }
}
