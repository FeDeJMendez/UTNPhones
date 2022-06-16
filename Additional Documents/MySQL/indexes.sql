
## DROP INDEX idx_CallStartTimeBetween ON calls;
CREATE INDEX idx_CallStartTimeBetween 
	ON calls (starttime, origin_phoneline_id) USING BTREE;

-- -- --
    
## DROP INDEX idx_CallStartTimeEquals ON calls;
CREATE INDEX idx_CallStartTimeEquals 
	ON calls(starttime, origin_phoneline_id) USING HASH;

## DROP INDEX idx_Phonelines ON phonelines;
CREATE INDEX idx_Phonelines 
	ON phonelines (city_id) USING HASH;

## DROP INDEX idx_PersonsPhonelines ON persons;
CREATE UNIQUE INDEX idx_PersonsPhonelines 
	ON persons (phoneline_id) USING HASH;


        
        
        
        
        