package com.mrumstajn.gamedevforum.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopPostResponse {
  private int pageNumber;

  private PostResponse post;
}
