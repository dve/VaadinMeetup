ALTER TABLE BREAKOUT DROP CONSTRAINT FK_BREAKOUT_SUBMITTEDBYUSER_MEETUPID;
ALTER TABLE BREAKOUT_USERTAB DROP CONSTRAINT FK_BREAKOUT_USERTAB_Breakout_ID;
ALTER TABLE BREAKOUT_USERTAB DROP CONSTRAINT FK_BREAKOUT_USERTAB_likedByUsers_MEETUPID;
DROP TABLE BREAKOUT;
DROP TABLE IMPRINT;
DROP TABLE TALK;
DROP TABLE USERTAB;
DROP TABLE BREAKOUT_USERTAB;
