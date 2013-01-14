CREATE OR REPLACE FUNCTION dp_ins_trace() 
RETURNS void AS $$
BEGIN

	-- All Data nodes

	INSERT INTO data VALUES ('ai1', 'd', 'Anatomy Image 1', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ah1', 'd', 'Anatomy Header 1', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ai2', 'd', 'Anatomy Image 2', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ah2', 'd', 'Anatomy Header 2', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ai3', 'd', 'Anatomy Image 3', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ah3', 'd', 'Anatomy Header 3', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ai4', 'd', 'Anatomy Image 4', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ah4', 'd', 'Anatomy Header 4', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ri', 'd', 'Reference Image', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('rh', 'd', 'Reference Header', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('wp1', 'd', 'Warp Params 1', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('wp2', 'd', 'Warp Params 1', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('wp3', 'd', 'Warp Params 1', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('wp4', 'd', 'Warp Params 1', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('si1', 'd', 'Resliced Image 1', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('sh1', 'd', 'Resliced Header 1', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('si2', 'd', 'Resliced Image 2', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('sh2', 'd', 'Resliced Header 2', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('si3', 'd', 'Resliced Image 3', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('sh3', 'd', 'Resliced Header 3', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('si4', 'd', 'Resliced Image 4', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('sh4', 'd', 'Resliced Header 4', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ai', 'd', 'Atlas Image', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ah', 'd', 'Atlas Header', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('axs', 'd', 'Atlas X Slice', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ays', 'd', 'Atlas Y Slice', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('azs', 'd', 'Atlas Z Slice', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('axg', 'd', 'Atlas X Graphic', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('ayg', 'd', 'Atlas Y Graphic', 'v', 'No value for now',NULL);
	INSERT INTO data VALUES ('azg', 'd', 'Atlas Z Graphic', 'v', 'No value for now',NULL);


	-- All Invocation nodes

	INSERT INTO actor VALUES ('aw1', 'i', 'Align Warp 1', 'v', 'No value for now', 'aw');
	INSERT INTO actor VALUES ('aw2', 'i', 'Align Warp 1', 'v', 'No value for now', 'aw');
	INSERT INTO actor VALUES ('aw3', 'i', 'Align Warp 1', 'v', 'No value for now', 'aw');
	INSERT INTO actor VALUES ('aw4', 'i', 'Align Warp 1', 'v', 'No value for now', 'aw');
	INSERT INTO actor VALUES ('rs1', 'i', 'Reslice 1', 'v', 'No value for now', 'rs');
	INSERT INTO actor VALUES ('rs2', 'i', 'Reslice 2', 'v', 'No value for now', 'rs');
	INSERT INTO actor VALUES ('rs3', 'i', 'Reslice 3', 'v', 'No value for now', 'rs');
	INSERT INTO actor VALUES ('rs4', 'i', 'Reslice 4', 'v', 'No value for now', 'rs');
	INSERT INTO actor VALUES ('sm1', 'i', 'Softmean', 'v', 'No value for now', 'sm');
	INSERT INTO actor VALUES ('sl1', 'i', 'Slicer 1', 'v', 'No value for now', 'sl');
	INSERT INTO actor VALUES ('sl2', 'i', 'Slicer 2', 'v', 'No value for now', 'sl');
	INSERT INTO actor VALUES ('sl3', 'i', 'Slicer 3', 'v', 'No value for now', 'sl');
	INSERT INTO actor VALUES ('cn1', 'i', 'Convert 1', 'v', 'No value for now', 'cn');
	INSERT INTO actor VALUES ('cn2', 'i', 'Convert 2', 'v', 'No value for now', 'cn');
	INSERT INTO actor VALUES ('cn3', 'i', 'Convert 3', 'v', 'No value for now', 'cn');


	-- Trace Edges

--	INSERT INTO edge VALUES (1, 'ai1', 'aw1', 'u');
--	INSERT INTO edge VALUES (1, 'ah1', 'aw1', 'u');
--	INSERT INTO edge VALUES (1, 'ri', 'aw1', 'u');
--	INSERT INTO edge VALUES (1, 'rh', 'aw1', 'u');
--	INSERT INTO edge VALUES (1, 'ai2', 'aw2', 'u');
--	INSERT INTO edge VALUES (1, 'ah2', 'aw2', 'u');
--	INSERT INTO edge VALUES (1, 'ri', 'aw2', 'u');
--	INSERT INTO edge VALUES (1, 'rh', 'aw2', 'u');
--	INSERT INTO edge VALUES (1, 'ai3', 'aw3', 'u');
--	INSERT INTO edge VALUES (1, 'ah3', 'aw3', 'u');
--	INSERT INTO edge VALUES (1, 'ri', 'aw3', 'u');
--	INSERT INTO edge VALUES (1, 'rh', 'aw3', 'u');
--	INSERT INTO edge VALUES (1, 'ai4', 'aw4', 'u');
--	INSERT INTO edge VALUES (1, 'ah4', 'aw4', 'u');
--	INSERT INTO edge VALUES (1, 'ri', 'aw4', 'u');
--	INSERT INTO edge VALUES (1, 'rh', 'aw4', 'u');
--	INSERT INTO edge VALUES (1, 'aw1', 'wp1', 'g');
--	INSERT INTO edge VALUES (1, 'aw2', 'wp2', 'g');
--	INSERT INTO edge VALUES (1, 'aw3', 'wp3', 'g');
--	INSERT INTO edge VALUES (1, 'aw4', 'wp4', 'g');
--	INSERT INTO edge VALUES (1, 'wp1', 'rs1', 'u');
--	INSERT INTO edge VALUES (1, 'wp2', 'rs2', 'u');
--	INSERT INTO edge VALUES (1, 'wp3', 'rs3', 'u');
--	INSERT INTO edge VALUES (1, 'wp4', 'rs4', 'u');
--	INSERT INTO edge VALUES (1, 'rs1', 'si1', 'g');
--	INSERT INTO edge VALUES (1, 'rs1', 'sh1', 'g');
--	INSERT INTO edge VALUES (1, 'rs2', 'si2', 'g');
--	INSERT INTO edge VALUES (1, 'rs2', 'sh2', 'g');
--	INSERT INTO edge VALUES (1, 'rs3', 'si3', 'g');
--	INSERT INTO edge VALUES (1, 'rs3', 'sh3', 'g');
--	INSERT INTO edge VALUES (1, 'rs4', 'si4', 'g');
--	INSERT INTO edge VALUES (1, 'rs4', 'sh4', 'g');
	INSERT INTO edge VALUES (1, 'si1', 'sm1', 'u');
	INSERT INTO edge VALUES (1, 'si2', 'sm1', 'u');
	INSERT INTO edge VALUES (1, 'si3', 'sm1', 'u');
	INSERT INTO edge VALUES (1, 'si4', 'sm1', 'u');
--	INSERT INTO edge VALUES (1, 'sh1', 'sm1', 'u');
--	INSERT INTO edge VALUES (1, 'sh2', 'sm1', 'u');
--	INSERT INTO edge VALUES (1, 'sh3', 'sm1', 'u');
--	INSERT INTO edge VALUES (1, 'sh4', 'sm1', 'u');
	INSERT INTO edge VALUES (1, 'sm1', 'ai', 'g');
	INSERT INTO edge VALUES (1, 'sm1', 'ah', 'g');
	INSERT INTO edge VALUES (1, 'ai', 'sl1', 'u');
	INSERT INTO edge VALUES (1, 'ai', 'sl2', 'u');
	INSERT INTO edge VALUES (1, 'ai', 'sl3', 'u');
	INSERT INTO edge VALUES (1, 'ah', 'sl1', 'u');
	INSERT INTO edge VALUES (1, 'ah', 'sl2', 'u');
	INSERT INTO edge VALUES (1, 'ah', 'sl3', 'u');
	INSERT INTO edge VALUES (1, 'sl1', 'axs', 'g');
	INSERT INTO edge VALUES (1, 'sl2', 'ays', 'g');
	INSERT INTO edge VALUES (1, 'sl3', 'azs', 'g');
	INSERT INTO edge VALUES (1, 'axs', 'cn1', 'u');
	INSERT INTO edge VALUES (1, 'ays', 'cn2', 'u');
	INSERT INTO edge VALUES (1, 'azs', 'cn3', 'u');
	INSERT INTO edge VALUES (1, 'cn1', 'axg', 'g');
	INSERT INTO edge VALUES (1, 'cn2', 'ayg', 'g');
	INSERT INTO edge VALUES (1, 'cn3', 'azg', 'g');

END;
$$ LANGUAGE plpgsql;