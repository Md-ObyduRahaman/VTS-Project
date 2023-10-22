package nex.vts.backend.repoImpl;

//@Service
/*public class DriverInfoImpl implements DriverInfoRepo {
    private final Logger logger = LoggerFactory.getLogger(RepoNexCorporateClient.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    String sql = null;

   public Optional<DriverInfoModel> findDriverInfo(Integer userID) {
        String sql = "select * from NEX_DRIVERINFO WHERE USERID=?";
        Optional<DriverInfoModel> driverInfoOptional = Optional.empty();

        try {
            DriverInfoModel driverInfo = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{userID},
                    (ResultSet rs, int rowNum) -> {
               //         DriverInfoModel model = new DriverInfoModel();
                  //      return model;
                   // }
            );

            //Optional<DriverInfoModel> driverInfoOptional = Optional.empty();

            try {

            //    driverInfoOptional = Optional.of(DriverInfoModel) jdbcTemplate.query(sql,
                       // BeanPropertyRowMapper.newInstance(DriverInfoModel.class));
            } catch (BadSqlGrammarException e) {
                logger.trace("No Data found with profileId is {}  Sql Grammar Exception", userID);
                throw new AppCommonException(4001 + "##Sql Grammar Exception");
            } catch (TransientDataAccessException f) {
                logger.trace("No Data found with profileId is {} network or driver issue or db is temporarily unavailable  ", userID);
                throw new AppCommonException(4002 + "##Network or driver issue or db is temporarily unavailable");
            } catch (CannotGetJdbcConnectionException g) {
                logger.trace("No Data found with profileId is {} could not acquire a jdbc connection  ", userID);
                throw new AppCommonException(4003 + "##A database connection could not be obtained");
            }

            if (!driverInfoOptional.isPresent()) {
                return Optional.empty();
            } else {
                return driverInfoOptional;
            }


        }

    }
}*/