-- Flyway V0: 사용자 서비스의 초기 스키마 (베이스라인) 정의

-- 1. p_badge 테이블 (Auditable 반영)
CREATE TABLE p_badge (
                         badge_id UUID NOT NULL,
                         badge_name VARCHAR(20) NOT NULL,
                         min_point BIGINT NOT NULL,
                         descriptions VARCHAR(255),
                         img_url VARCHAR(255),
    -- Auditable fields
                         created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                         created_by VARCHAR(255),
                         updated_at TIMESTAMP WITHOUT TIME ZONE,
                         updated_by VARCHAR(255),
                         deleted_at TIMESTAMP WITHOUT TIME ZONE,
                         deleted_by VARCHAR(255),

                         PRIMARY KEY (badge_id),
                         UNIQUE (badge_name)
);

-- 2. p_point_balance 테이블 (Auditable 반영)
CREATE TABLE p_point_balance (
                                 user_id UUID NOT NULL,
                                 current_balance INTEGER NOT NULL,
                                 total_accumulated_balance BIGINT NOT NULL,
                                 current_badge_id UUID,
    -- Auditable fields
                                 created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                 created_by VARCHAR(255),
                                 updated_at TIMESTAMP WITHOUT TIME ZONE,
                                 updated_by VARCHAR(255),
                                 deleted_at TIMESTAMP WITHOUT TIME ZONE,
                                 deleted_by VARCHAR(255),

                                 PRIMARY KEY (user_id)
);

-- 3. p_point_transaction 테이블 (Auditable 반영)
CREATE TABLE p_point_transaction (
                                     transaction_id UUID NOT NULL,
                                     user_id UUID,
                                     source_table VARCHAR(255),
                                     source_id UUID NOT NULL,
                                     type VARCHAR(255) NOT NULL,
                                     amount INTEGER NOT NULL,
                                     after_balance INTEGER NOT NULL,
                                     original_transaction_id UUID,
                                     cancelled BOOLEAN,
    -- Auditable fields
                                     created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                     created_by VARCHAR(255),
                                     updated_at TIMESTAMP WITHOUT TIME ZONE,
                                     updated_by VARCHAR(255),
                                     deleted_at TIMESTAMP WITHOUT TIME ZONE,
                                     deleted_by VARCHAR(255),

                                     PRIMARY KEY (transaction_id)
);

-- 4. p_reward 테이블 (Auditable 반영)
CREATE TABLE p_reward (
                          reward_id UUID NOT NULL,
                          reward_name VARCHAR(100) NOT NULL,
                          price INTEGER NOT NULL,
                          descriptions VARCHAR(255),
    -- Auditable fields
                          created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          created_by VARCHAR(255),
                          updated_at TIMESTAMP WITHOUT TIME ZONE,
                          updated_by VARCHAR(255),
                          deleted_at TIMESTAMP WITHOUT TIME ZONE,
                          deleted_by VARCHAR(255),

                          PRIMARY KEY (reward_id)
);

-- 5. p_user_reward 테이블
CREATE TABLE p_user_reward (
                               id UUID NOT NULL,
                               user_id UUID NOT NULL,
                               reward_id UUID NOT NULL,
                               acquired_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                               status VARCHAR(255) NOT NULL,
    -- RewardSnapshot fields
                               reward_snapshot_reward_name VARCHAR(255),
                               reward_snapshot_acquired_price INTEGER,
                               reward_snapshot_descriptions VARCHAR(255),

                               PRIMARY KEY (id),
                               UNIQUE (user_id, reward_id)
);

-- 6. 외래 키 추가 (Foreign Key Constraints)

-- p_point_balance의 current_badge_id -> p_badge.badge_id
ALTER TABLE p_point_balance
    ADD CONSTRAINT fk_p_point_balance_current_badge_id
        FOREIGN KEY (current_badge_id)
            REFERENCES p_badge (badge_id);

-- p_user_reward의 reward_id -> p_reward.reward_id
ALTER TABLE p_user_reward
    ADD CONSTRAINT fk_p_user_reward_reward_id
        FOREIGN KEY (reward_id)
            REFERENCES p_reward (reward_id);

-- 7. Enum 값 무결성을 위한 CHECK 제약 조건 추가

-- p_point_transaction 테이블의 type 필드에 CHECK 제약 조건 추가
ALTER TABLE p_point_transaction
    ADD CONSTRAINT check_p_point_transaction_type
        CHECK (type IN ('GAIN', 'USE', 'CANCEL_GAIN', 'CANCEL_USE'));

-- p_user_reward 테이블의 status 필드에 CHECK 제약 조건 추가
ALTER TABLE p_user_reward
    ADD CONSTRAINT check_p_user_reward_status
        CHECK (status IN ('AVAILABLE', 'USED'));