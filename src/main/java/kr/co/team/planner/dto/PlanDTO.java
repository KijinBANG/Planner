package kr.co.team.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {

    private Long pno;
    private String title;
    private String description;
    private String location;

    @Builder.Default
    private List<PlanImageDTO> imageDTOList = new ArrayList<>();

    //Plan 의 평균 평점
    private double avg;
    //리뷰 수 jpa의 count()
    private long reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
