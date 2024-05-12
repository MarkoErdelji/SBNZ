INSERT INTO users (suspicion_level, username, password, user_type)
VALUES ('LOW', 'admin', '$2a$10$TjOdcQyhcvNZSRQ2e9/CfeMWW4sPUAVGzFFkoMZpv4.Ku.LhNU3FS', 0);

-- Insert regular user
INSERT INTO users (suspicion_level, username, password, user_type)
VALUES ('LOW', 'user', '$2a$10$TjOdcQyhcvNZSRQ2e9/CfeMWW4sPUAVGzFFkoMZpv4.Ku.LhNU3FS', 1);
