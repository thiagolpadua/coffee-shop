package org.acme.endpoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.dto.ItemDto;
import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.services.ItemCreateService;
import org.acme.services.ItemDeleteService;
import org.acme.services.ItemListService;
import org.acme.services.ItemUpdateService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/item")
@Tag(name = "Item", description = "Item CRUD")
@RequestScoped
public class ItemEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(ItemEndpoint.class.getName());

        @Inject
        ItemCreateService itemCreateService;

        @Inject
        ItemListService itemListService;

        @Inject
        ItemUpdateService itemUpdateService;

        @Inject
        ItemDeleteService itemDeleteService;

        @POST
        @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Create a Item", description = "Create a Item")
        @APIResponse(responseCode = "200", description = "OK", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDto.class)) })
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response create(ItemDto request) throws BusinessErrorException {

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(request);

                LOG.debug("ItemRest + POST - request - " + jsonString);

                ItemDto response = itemCreateService.CreateItemService(request);

                jsonString = jsonb.toJson(response);
                LOG.debug("ItemRest + POST - response - " + jsonString);

                return Response.status(Response.Status.OK).entity(response).build();

        }

        @GET
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "List Item", description = "List items")
        @APIResponse(responseCode = "200", description = "OK", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDto[].class)) })
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response list() throws BusinessErrorException {

                LOG.debug("ItemRest + GET");

                ItemDto[] response = itemListService.listItemService();

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(response);

                LOG.debug("Response + GET - response - " + jsonString);

                return Response.status(Response.Status.OK).entity(response).build();

        }

        @PUT
        @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Update Item", description = "Update a item")
        @APIResponse(responseCode = "200", description = "Success")
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response update(ItemDto request) throws BusinessErrorException {

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(request);

                LOG.debug("ItemRest + PUT - request - " + jsonString);

                ItemDto response = itemUpdateService.updateItemService(request);

                jsonString = jsonb.toJson(response);
                LOG.debug("ItemRest + PUT - response - " + jsonString);

                return Response.status(Response.Status.OK).entity(response).build();

        }

        @DELETE
        @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Delete Item", description = "Delete a item")
        @APIResponse(responseCode = "200", description = "Success")
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response delete(
                        @Parameter(description = "The item id to delete.", required = true) @PathParam("id") int id)
                        throws BusinessErrorException {

                LOG.debug("ItemRest + DELETE - id=[" + id + "]");

                itemDeleteService.deleteItemService(id);

                LOG.debug("ItemRest + DELETE - end");

                return Response.status(Response.Status.OK).build();

        }
    
}
