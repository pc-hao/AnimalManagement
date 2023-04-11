DROP EVENT IF EXISTS clean_verification_event;
CREATE EVENT clean_verification_event
    ON SCHEDULE EVERY 20 MINUTE STARTS NOW()
    DO delete
       from verification
       where TIMESTAMPDIFF(MINUTE, start_time, NOW()) > 10;