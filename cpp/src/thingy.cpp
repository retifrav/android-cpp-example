#include <sstream>

#include <Thingy/thingy.h>
#include "stuff.h"

#include <json/json.h>

namespace dpndnc
{
    std::string doThingy()
    {
        std::stringstream someThing;
        someThing << "a string from C++: " << thingyString;
        return someThing.str();
    }

    std::string whoHasTheBestBoobs(std::string jsonString)
    {
        Json::Value root;
        Json::Reader reader;
        if (!reader.parse(jsonString, root))
        {
            // lazy-ass errors reporting
            return "[ERROR] Couldn't parse this JSON";
        }

        // there should be a check for whether this key is even there
        auto grils = root["grils"];

        int bestBoobs = 0;
        std::string bestGirl = "Unknown";
        for (Json::Value::ArrayIndex i = 0; i != grils.size(); i++)
        {
            // there should be checks for whether these keys are even there
            int currentBoobs = grils[i]["boobs"].asInt();
            std::string currentGirl = grils[i]["name"].asString();

            if (currentBoobs > bestBoobs)
            {
                bestBoobs = currentBoobs;
                bestGirl = grils[i]["name"].asString();
            }
            else if (currentBoobs == bestBoobs)
            {
                std::stringstream bestGirls;
                bestGirls << bestGirl << ", " << currentGirl;
                bestGirl = bestGirls.str();
            }
        }

        if (bestBoobs == 0)
        {
            // yet another lazy-ass errors reporting
            return "[ERROR] Couldn't determine the best gril";
        }
        else { return bestGirl; }
    }
}
