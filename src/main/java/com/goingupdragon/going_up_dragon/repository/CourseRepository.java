package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.Course;
import com.goingupdragon.going_up_dragon.enums.Enums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c WHERE c.courseId = :courseId")
    Course findCourse (@Param("courseId") Integer courseId);

    @Query ("SELECT c FROM Course c WHERE c.instructor.infoId = :infoId")
    List<Course> findInstructorCourses (@Param("infoId")Integer infoId);

    @Query("SELECT c FROM Course c WHERE c.courseId IN :courseIds")
    List<Course> findCoursesByIds(@Param("courseIds") List<Integer> courseIds);

    @Query(value = """
    SELECT * FROM courses c
    WHERE price = 0
    AND NOT EXISTS (
        SELECT 1 FROM enrollments e WHERE e.course_id = c.course_id AND e.info_id = :infoId
    )
    ORDER BY RAND() 
    LIMIT :limit
    """, nativeQuery = true)
    List<Course> findFreeCoursesExcludingEnrolled(@Param("infoId") int infoId, @Param("limit") int limit);

    @Query(value = """
    SELECT * FROM courses
    WHERE level = :#{#level.toString()}
    AND course_id NOT IN (
        SELECT e.course_id FROM enrollments e WHERE e.info_id = :infoId
    )
    ORDER BY RAND()
    LIMIT :limit
""", nativeQuery = true)
    List<Course> findCoursesByLevel(@Param("level") Enums.CourseLevel level, @Param("infoId") int infoId, @Param("limit") int limit);

//    @Query(value = """
//        SELECT c.*,
//               COUNT(e.course_id) AS enroll_count,
//               AVG(r.rate) AS avg_rate,
//               COUNT(l.course_id) AS like_count
//        FROM courses c
//        LEFT JOIN enrollments e ON e.course_id = c.course_id
//        LEFT JOIN review r ON r.course_id = c.course_id
//        LEFT JOIN like_table l ON l.course_id = c.course_id
//        WHERE (:level IS NULL OR c.level = :#{#level.toString()})
//          AND (:language IS NULL OR c.language = :#{#language.toString()})
//          AND (
//                (:timeFilter = 'short' AND c.duration BETWEEN 0 AND 10800)
//                OR (:timeFilter = 'medium' AND c.duration BETWEEN 10801 AND 36000)
//                OR (:timeFilter = 'long' AND c.duration > 36000)
//                OR (:timeFilter IS NULL)
//            )
//        GROUP BY c.course_id  -- GROUP BY가 필요합니다.
//        ORDER BY
//            CASE
//                WHEN :sortBy = 'latest' THEN c.start_date
//                WHEN :sortBy = 'popularity' THEN enroll_count
//                WHEN :sortBy = 'rating' THEN avg_rate
//                WHEN :sortBy = 'likes' THEN like_count
//                ELSE c.course_id  -- 기본 정렬 (오래된 강의 순)
//            END DESC
//        LIMIT :size OFFSET :offset
//    """, nativeQuery = true)
//    List<Course> findCoursesByFilters(
//            @Param("level") Enums.CourseLevel level,
//            @Param("language") Enums.CourseLanguage language,
//            @Param("timeFilter") String timeFilter,
//            @Param("sortBy") String sortBy,
//            @Param("size") int size,
//            @Param("offset") int offset
//    );

//    @Query(value = """
//        SELECT COUNT(*)
//        FROM courses c
//        LEFT JOIN enrollments e ON e.course_id = c.course_id
//        LEFT JOIN review r ON r.course_id = c.course_id
//        LEFT JOIN like_table l ON l.course_id = c.course_id
//        WHERE (:level IS NULL OR c.level = :#{#level.toString()})
//          AND (:language IS NULL OR c.language = :#{#language.toString()})
//          AND (
//                (:timeFilter = 'short' AND c.duration BETWEEN 0 AND 10800)
//                OR (:timeFilter = 'medium' AND c.duration BETWEEN 10801 AND 36000)
//                OR (:timeFilter = 'long' AND c.duration > 36000)
//                OR (:timeFilter IS NULL)
//            )
//    """, nativeQuery = true)
//    int countCoursesByFilters(
//            @Param("level") Enums.CourseLevel level,
//            @Param("language") Enums.CourseLanguage language,
//            @Param("timeFilter") String timeFilter
//    );

    @Query(value = """
    SELECT c.*,
        (SELECT IFNULL(COUNT(*), 0) FROM enrollments e WHERE e.course_id = c.course_id) AS enroll_count,
        (SELECT IFNULL(AVG(r.rate), 0) FROM review r WHERE r.course_id = c.course_id) AS avg_rating,
        (SELECT IFNULL(COUNT(*), 0) FROM like_table l WHERE l.course_id = c.course_id) AS like_count
    FROM courses c
    WHERE (:mainCategory = 0 OR c.main_category_id = :mainCategory)
      AND (:subCategory = 0 OR c.sub_category_id = :subCategory)
      AND (:#{#level.toString()} = '모두' OR c.level = :#{#level.toString()})
      AND (:#{#language.toString()} = '모두' OR c.language = :#{#language.toString()})
      AND (
            (:timeFilter IS NULL)
            OR (:timeFilter = 'short' AND c.duration BETWEEN 0 AND 36000)
            OR (:timeFilter = 'medium' AND c.duration BETWEEN 36001 AND 144000)
            OR (:timeFilter = 'long' AND c.duration > 144000)
        )
      AND (
            :selectedTags IS NULL
            OR c.subject_tag1 IN (:selectedTags)
            OR c.subject_tag2 IN (:selectedTags)
            OR c.subject_tag3 IN (:selectedTags)
        )
    ORDER BY
        CASE WHEN :sortBy = 'latest' THEN c.start_date END DESC,
        CASE WHEN :sortBy = 'popularity' THEN enroll_count END DESC,
        CASE WHEN :sortBy = 'rating' THEN avg_rating END DESC,
        CASE WHEN :sortBy = 'likes' THEN like_count END DESC,
        c.course_id DESC
    LIMIT :size OFFSET :offset
    """, nativeQuery = true)
    List<Course> findCoursesByFiltersAndSort(
            @Param("mainCategory") Integer mainCategory,
            @Param("subCategory") Integer subCategory,
            @Param("level") Enums.CourseLevel level,
            @Param("language") Enums.CourseLanguage language,
            @Param("timeFilter") String timeFilter,
            @Param("selectedTags") Collection<Integer> selectedTags,
            @Param("sortBy") String sortBy,
            @Param("size") int size,
            @Param("offset") int offset
    );

    @Query(value = """
    SELECT COUNT(*)
    FROM courses c
    WHERE (:mainCategory = 0 OR c.main_category_id = :mainCategory)
      AND (:subCategory = 0 OR c.sub_category_id = :subCategory)
      AND (:#{#level.toString()} = '모두' OR c.level = :#{#level.toString()})
      AND (:#{#language.toString()} = '모두' OR c.language = :#{#language.toString()})
      AND (
            (:timeFilter IS NULL)
            OR (:timeFilter = 'short' AND c.duration BETWEEN 0 AND 36000)
            OR (:timeFilter = 'medium' AND c.duration BETWEEN 36001 AND 144000)
            OR (:timeFilter = 'long' AND c.duration > 144000)
        )
      AND (
            :selectedTags IS NULL
            OR c.subject_tag1 IN (:selectedTags)
            OR c.subject_tag2 IN (:selectedTags)
            OR c.subject_tag3 IN (:selectedTags)
        )
    """, nativeQuery = true)
    int countCoursesByFilters(
            @Param("mainCategory") Integer mainCategory,
            @Param("subCategory") Integer subCategory,
            @Param("level") Enums.CourseLevel level,
            @Param("language") Enums.CourseLanguage language,
            @Param("timeFilter") String timeFilter,
            @Param("selectedTags") Collection<Integer> selectedTags
    );
}
