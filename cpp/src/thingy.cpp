#include <sstream>

#include <Thingy/thingy.h>
#include "stuff.h"

namespace dpndnc
{
    std::string doThingy()
    {
        std::stringstream someThing;
        someThing << "a string from C++: " << thingyString;
        return someThing.str();
    }
}
