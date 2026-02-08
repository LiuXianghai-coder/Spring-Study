WITH RECURSIVE cte AS (
    SELECT id, created_time, updated_time
    FROM rate_info ri1
    WHERE ri1.id = 1

    UNION
    SELECT id, DATE_ADD(created_time, INTERVAL 1 DAY) AS created_time, updated_time
    FROM cte
    WHERE cte.created_time < DATE_ADD(cte.updated_time, INTERVAL -1 DAY)
)
SELECT * FROM cte;