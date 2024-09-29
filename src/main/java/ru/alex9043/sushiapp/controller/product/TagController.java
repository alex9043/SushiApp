package ru.alex9043.sushiapp.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.error.ErrorMessageDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagRequestDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagResponseDTO;
import ru.alex9043.sushiapp.DTO.product.tag.TagsResponseDTO;
import ru.alex9043.sushiapp.service.product.TagService;

@RestController
@RequestMapping("/products/tags")
@RequiredArgsConstructor
@Schema(description = "Tag")
@Validated
public class TagController {
    private final TagService tagService;

    @Operation(summary = "Get all tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tags get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TagsResponseDTO.class))),
    })
    @GetMapping
    public TagsResponseDTO getTags() {
        return tagService.getTags();
    }

    @Operation(summary = "Crate a new tag",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Tag created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TagResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TagRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public TagsResponseDTO createTag(
            @Valid @RequestBody TagRequestDTO tagRequestDTO
    ) {
        return tagService.createTag(tagRequestDTO);
    }

    @Operation(summary = "Update tag",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Tag updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TagsResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TagRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{tagId}")
    public TagsResponseDTO updateTag(
            @Valid @RequestBody TagRequestDTO tagRequestDTO,
            @PathVariable(name = "tagId") Long tagId
    ) {
        return tagService.updateTag(tagRequestDTO, tagId);
    }

    @Operation(summary = "Delete tag",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Tag updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TagsResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{tagId}")
    public TagsResponseDTO deleteTag(
            @PathVariable(name = "tagId") Long tagId
    ) {
        return tagService.deleteTag(tagId);
    }
}

