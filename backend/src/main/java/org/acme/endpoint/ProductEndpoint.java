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

import org.acme.dto.ProductDto;
import org.acme.exceptions.BusinessError;
import org.acme.exceptions.BusinessErrorException;
import org.acme.services.ProductCreateService;
import org.acme.services.ProductDeleteService;
import org.acme.services.ProductGetService;
import org.acme.services.ProductListService;
import org.acme.services.ProductUpdateService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/product")
@Tag(name = "Product", description = "Product CRUD")
@RequestScoped
public class ProductEndpoint {

        private static final Logger LOG = LoggerFactory.getLogger(ProductEndpoint.class.getName());

        @Inject
        ProductCreateService productCreateService;

        @Inject
        ProductListService productListService;

        @Inject
        ProductUpdateService productUpdateService;

        @Inject
        ProductDeleteService productDeleteService;

        @Inject
        ProductGetService productGetService;

        @POST
        @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Create a Product", description = "Create a Product")
        @APIResponse(responseCode = "200", description = "OK", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)) })
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response create(ProductDto request) throws BusinessErrorException {

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(request);

                LOG.debug("ProductRest + POST - request - " + jsonString);

                ProductDto response = productCreateService.CreateProductService(request);

                jsonString = jsonb.toJson(response);
                LOG.debug("ProductRest + POST - response - " + jsonString);

                return Response.status(Response.Status.OK).entity(response).build();

        }

        @GET
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "List Product", description = "List products")
        @APIResponse(responseCode = "200", description = "OK", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto[].class)) })
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response list() {

                LOG.debug("ProductRest + GET");

                ProductDto[] response = productListService.listProductService();

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(response);

                LOG.debug("Response + GET - response - " + jsonString);

                return Response.status(Response.Status.OK).entity(response).build();

        }

        @PUT
        @Path("{id}")
        @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Update Product", description = "Update a product")
        @APIResponse(responseCode = "200", description = "Success")
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response update(ProductDto request,
                        @Parameter(description = "The product id to delete.", required = true) @PathParam("id") int id)
                        throws BusinessErrorException {

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(request);

                LOG.debug("ProductRest + PUT - request - " + jsonString);

                ProductDto response = productUpdateService.updateProductService(request);

                jsonString = jsonb.toJson(response);
                LOG.debug("ProductRest + PUT - response - " + jsonString);

                return Response.status(Response.Status.OK).entity(response).build();

        }

        @DELETE
        @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Delete Product", description = "Delete a product")
        @APIResponse(responseCode = "200", description = "Success")
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response delete(
                        @Parameter(description = "The product id to delete.", required = true) @PathParam("id") int id)
                        throws BusinessErrorException {

                LOG.debug("ProductRest + DELETE - id=[" + id + "]");

                productDeleteService.deleteProductService(id);

                LOG.debug("ProductRest + DELETE - end");

                return Response.status(Response.Status.OK).build();

        }

        @GET
        @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
        @Operation(summary = "Get Product By Id", description = "Get a product By Id")
        @APIResponse(responseCode = "200", description = "OK", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto[].class)) })
        @APIResponse(responseCode = "422", description = "Business Error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        @APIResponse(responseCode = "500", description = "System error", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessError.class)) })
        public Response get(@Parameter(description = "The product id to get.", required = true) @PathParam("id") int id)
                        throws BusinessErrorException {

                LOG.debug("ProductRest + GET");

                ProductDto response = productGetService.getProductService(id);

                Jsonb jsonb = JsonbBuilder.create();
                String jsonString = jsonb.toJson(response);

                LOG.debug("Response + GET - response - " + jsonString);

                return Response.status(Response.Status.OK).entity(response).build();

        }

}
