package com.jjans.BB.Controller;
import com.jjans.BB.Dto.CommentRequestDto;
import com.jjans.BB.Dto.CommentResponseDto;
import com.jjans.BB.Service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/save/{feedId}")
    public ResponseEntity<?> saveComment(
            @PathVariable Long feedId,
            @RequestBody CommentRequestDto commentDto
    ) {
        try {
            CommentResponseDto savedComment = commentService.saveComments(feedId, commentDto);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("댓글 저장에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/update/{feedId}/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long feedId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentDto
    ) {
        try {
            CommentResponseDto updatedComment = commentService.updateComments(feedId, commentId, commentDto);
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("댓글 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{feedId}/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long feedId,
            @PathVariable Long commentId
    ) {
        try {
            commentService.deleteComment(feedId, commentId);
            return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("댓글 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
