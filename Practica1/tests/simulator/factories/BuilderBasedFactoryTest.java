package simulator.factories;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.MoveAllStrategy;
import simulator.model.MoveFirstStrategy;
import simulator.model.NewCityRoad;
import simulator.model.NewInterCityRoad;
import simulator.model.NewJunction;
import simulator.model.NewVehicle;
import simulator.model.RoundRobinStrategy;
import simulator.model.SetContaminationClass;
import simulator.model.SetWeather;

class BuilderBasedFactoryTest {

	private static  Factory<LightSwitchingStrategy> createLSSFactory() {
		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		return new BuilderBasedFactory<>(lsbs);
	}

	private static  Factory<DequeuingStrategy> createDQSFactory() {
		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		return new BuilderBasedFactory<>(dqbs);
	}

	private static BuilderBasedFactory<Event> createEventsFactory() {

		List<Builder<Event>> eventBuilders = new ArrayList<>();

		eventBuilders.add(new NewJunctionBuilder(createLSSFactory(),createDQSFactory()));
		eventBuilders.add(new NewCityRoadBuilder());
		eventBuilders.add(new NewInterCityRoadBuilder());
		eventBuilders.add(new NewVehicleBuilder());
		eventBuilders.add(new SetWeatherEventBuilder());
		eventBuilders.add(new SetContClassEventBuilder());

		return new BuilderBasedFactory<>(eventBuilders);
	}

	@Test
	void test_1() {
		Factory<LightSwitchingStrategy> lssFactory = createLSSFactory();

		String inputJSon = "{ \"type\" : \"round_robin_lss\", \"data\" : {\"timeslot\" : 5} }";

		assertTrue(lssFactory.create_instance(new JSONObject(inputJSon)) instanceof RoundRobinStrategy);
	}

	@Test
	void test_2() {
		Factory<LightSwitchingStrategy> lssFactory = createLSSFactory();

		String inputJSon = "{ \"type\" : \"round_robin_lss\", \"data\" : {\"timeslot\" : 5} }";

		assertTrue(lssFactory.create_instance(new JSONObject(inputJSon)) instanceof RoundRobinStrategy);
	}

	@Test
	void test_3() {
		Factory<LightSwitchingStrategy> lssFactory = createLSSFactory();

		String inputJSon = "{ \"type\" : \"most_crowded_lss\", \"data\" : {\"timeslot\" : 5} }";

		assertTrue(lssFactory.create_instance(new JSONObject(inputJSon)) instanceof MostCrowdedStrategy);
	}

	@Test
	void test_4() {
		Factory<LightSwitchingStrategy> lssFactory = createLSSFactory();

		String inputJSon = "{ \"type\" : \"most_crowded_lss\", \"data\" : {\"timeslot\" : 5} }";

		assertTrue(lssFactory.create_instance(new JSONObject(inputJSon)) instanceof MostCrowdedStrategy);
	}

	@Test
	void test_5() {
		Factory<LightSwitchingStrategy> lssFactory = createLSSFactory();

		String inputJSon = "{ \"type\" : \"bla\", \"data\" : {\"timeslot\" : 5} }";

		// in principle, for invalid input we throw an exception, but returning null is
		// fine also
		assertThrows(Exception.class, () -> lssFactory.create_instance(new JSONObject(inputJSon)));
	}

	
	@Test
	void test_6() {
		Factory<DequeuingStrategy> dqsFactory = createDQSFactory();

		String inputJSon = "{ \"type\" : \"move_all_dqs\", \"data\" : {\"timeslot\" : 5} }";

		assertTrue(dqsFactory.create_instance(new JSONObject(inputJSon)) instanceof MoveAllStrategy);
	}

	@Test
	void test_7() {
		Factory<DequeuingStrategy> dqsFactory = createDQSFactory();

		String inputJSon = "{ \"type\" : \"move_all_dqs\", \"data\" : {\"timeslot\" : 5} }";

		assertTrue(dqsFactory.create_instance(new JSONObject(inputJSon)) instanceof MoveAllStrategy);
	}

	@Test
	void test_8() {
		Factory<DequeuingStrategy> dqsFactory = createDQSFactory();

		String inputJSon = "{ \"type\" : \"move_first_dqs\", \"data\" : {\"timeslot\" : 5} }";

		assertTrue(dqsFactory.create_instance(new JSONObject(inputJSon)) instanceof MoveFirstStrategy);
	}

	@Test
	void test_9() {
		Factory<DequeuingStrategy> dqsFactory = createDQSFactory();

		String inputJSon = "{ \"type\" : \"move_first_dqs\", \"data\" : {\"timeslot\" : 5} }";

		assertTrue(dqsFactory.create_instance(new JSONObject(inputJSon)) instanceof MoveFirstStrategy);
	}

	@Test
	void test_10() {
		Factory<DequeuingStrategy> dqsFactory = createDQSFactory();

		String inputJSon = "{ \"type\" : \"bla\", \"data\" : {\"timeslot\" : 5} }";

		// in principle, for invalid input we throw an exception, but returning null is
		// fine also
		assertThrows(Exception.class, () -> dqsFactory.create_instance(new JSONObject(inputJSon)));
	}

	@Test
	void test_11() {
		Factory<Event> eFactory = createEventsFactory();

		String inputJSon = " {\n"
				+ "     \"type\" : \"new_junction\",\n"
				+ "     \"data\" : {\n"
				+ "     	 \"time\" : 1,\n"
				+ "         \"id\"   : \"j1\",\n"
				+ "      	 \"coor\" : [100,200],\n"
				+ "      	 \"ls_strategy\" : { \"type\" : \"round_robin_lss\", \"data\" : {\"timeslot\" : 5} },\n"
				+ "      	 \"dq_strategy\" : { \"type\" : \"move_first_dqs\",  \"data\" : {} }\n"
				+ "   	 }\n"
				+ "   }";

		assertTrue( eFactory.create_instance(new JSONObject(inputJSon)) instanceof NewJunction);
	}

	@Test
	void test_12() {
		Factory<Event> eFactory = createEventsFactory();

		String inputJSon = "{\n"
				+ "	  \"type\" : \"new_city_road\",\n"
				+ "  	  \"data\" : {\n"
				+ "    	  \"time\"     : 1,\n"
				+ "    	   \"id\"       : \"r1\",\n"
				+ "           \"src\"      : \"j1\",\n"
				+ "           \"dest\"     : \"j3\",\n"
				+ "           \"length\"   : 10000,\n"
				+ "           \"co2limit\" : 500,\n"
				+ "           \"maxspeed\" : 120,\n"
				+ "           \"weather\"  : \"SUNNY\"\n"
				+ "   	  }\n"
				+ "   	}";

		assertTrue( eFactory.create_instance(new JSONObject(inputJSon)) instanceof NewCityRoad);
	}

	@Test
	void test_13() {
		Factory<Event> eFactory = createEventsFactory();

		String inputJSon = "{\n"
				+ "	  \"type\" : \"new_inter_city_road\",\n"
				+ "  	  \"data\" : {\n"
				+ "    	  \"time\"     : 1,\n"
				+ "    	   \"id\"       : \"r3\",\n"
				+ "           \"src\"      : \"j3\",\n"
				+ "           \"dest\"     : \"j4\",\n"
				+ "           \"length\"   : 10000,\n"
				+ "           \"co2limit\" : 500,\n"
				+ "           \"maxspeed\" : 120,\n"
				+ "           \"weather\"  : \"SUNNY\"\n"
				+ "   	  }\n"
				+ "   	}";

		assertTrue( eFactory.create_instance(new JSONObject(inputJSon)) instanceof NewInterCityRoad);
	}

	@Test
	void test_14() {
		Factory<Event> eFactory = createEventsFactory();

		String inputJSon = "{\n"
				+ "	  \"type\" : \"new_vehicle\",\n"
				+ "  	  \"data\" : {\n"
				+ "          \"time\"      : 1,\n"
				+ "          \"id\"        : \"v1\",\n"
				+ "          \"maxspeed\"  : 100,\n"
				+ "          \"class\"     : 3,\n"
				+ "          \"itinerary\" : [\"j1\", \"j3\", \"j4\"]\n"
				+ "      }\n"
				+ "    }";

		assertTrue( eFactory.create_instance(new JSONObject(inputJSon)) instanceof NewVehicle);
	}

	@Test
	void test_15() {
		Factory<Event> eFactory = createEventsFactory();

		String inputJSon = "{\n"
				+ "  	  \"type\" : \"set_weather\",\n"
				+ "      \"data\" : {\n"
				+ "          \"time\"     : 100,\n"
				+ "          \"info\"     : [ { \"road\" : \"r1\", \"weather\": \"SUNNY\" }, \n"
				+ "                         { \"road\" : \"r2\", \"weather\": \"STORM\" } \n"
				+ "	                   ]\n"
				+ "  	  }\n"
				+ "    }";

		assertTrue( eFactory.create_instance(new JSONObject(inputJSon)) instanceof SetWeather);
	}

	@Test
	void test_16() {
		Factory<Event> eFactory = createEventsFactory();

		String inputJSon = "{\n"
				+ "      \"type\" : \"set_cont_class\",\n"
				+ "      \"data\" : {\n"
				+ "          \"time\"     : 50,\n"
				+ "          \"info\"     : [ { \"vehicle\" : \"v1\", \"class\": 5 }, \n"
				+ "                         { \"vehicle\" : \"v2\", \"class\": 2 } \n"
				+ "                       ]\n"
				+ "      }\n"
				+ "    }";

		assertTrue( eFactory.create_instance(new JSONObject(inputJSon)) instanceof SetContaminationClass);
	}

	@Test
	void test_17() {
		Factory<Event> eFactory = createEventsFactory();

		String inputJSon = "{ \"type\" : \"bla\", \"data\" : {\"timeslot\" : 5} }";

		// in principle, for invalid input we throw an exception, but returning null is
		// fine also
		assertThrows(Exception.class, () -> eFactory.create_instance(new JSONObject(inputJSon)));
	}

	@Test
	void test_18() {
		Factory<Event> eFactory = createEventsFactory();

		String inputJSon = "{}";

		assertThrows(Exception.class, () -> eFactory.create_instance(new JSONObject(inputJSon)));
	}



}
